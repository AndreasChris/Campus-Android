package de.tum.in.tumcampusapp.component.ui.chat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.tum.in.tumcampusapp.R;
import de.tum.in.tumcampusapp.api.app.TUMCabeClient;
import de.tum.in.tumcampusapp.api.app.exception.NoPrivateKey;
import de.tum.in.tumcampusapp.component.other.generic.activity.BaseActivity;
import de.tum.in.tumcampusapp.component.ui.chat.model.ChatMember;
import de.tum.in.tumcampusapp.component.ui.chat.model.ChatRoom;
import de.tum.in.tumcampusapp.component.ui.chat.model.ChatVerification;
import de.tum.in.tumcampusapp.database.TcaDb;
import de.tum.in.tumcampusapp.utils.Const;
import de.tum.in.tumcampusapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Allows user to search for other users which he or she can then add to the ChatRoom
 */
public class AddChatMemberActivity extends BaseActivity {
    private static final int THRESHOLD = 3; // min number of characters before getting suggestions
    private static final int DELAY = 1000; // millis after user stopped typing before getting suggestions
    private String chatRoomName;
    private int roomId;
    private TUMCabeClient tumCabeClient;
    private Pattern tumIdPattern;
    private AutoCompleteTextView searchView;

    // for delayed suggestions
    private Handler delayHandler;
    private Runnable suggestionRunnable = () -> getSuggestions();


    private List<ChatMember> suggestions;

    public AddChatMemberActivity(){
        super(R.layout.activity_add_chat_member);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        suggestions = new ArrayList<>();
        delayHandler = new Handler();
        tumIdPattern = Pattern.compile(Const.TUM_ID_PATTERN);

        chatRoomName = getIntent().getStringExtra(Const.CHAT_ROOM_DISPLAY_NAME);
        roomId = getIntent().getIntExtra(Const.CURRENT_CHAT_ROOM, -1);
        Utils.log("ChatRoom: " + chatRoomName + " (roomId: " + roomId + ")");

        tumCabeClient = TUMCabeClient.getInstance(this);

        searchView = findViewById(R.id.chat_user_search);
        searchView.setThreshold(THRESHOLD);
        searchView.setAdapter(new MemberSuggestionsListAdapter(this, suggestions));

        searchView.setOnItemClickListener((adapterView, view, pos, l) -> {
            ChatMember member = (ChatMember) adapterView.getItemAtPosition(pos);
            showConfirmDialog(member);
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // do nothing, we want to know the new input -> onTextChanged
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                delayHandler.removeCallbacks(suggestionRunnable);

                if(charSequence.length() < THRESHOLD){
                    return;
                }

                // backend call, add to adapter
                if(tumIdPattern.matcher(charSequence).matches()){
                    // query matches TUM-ID
                    tumCabeClient.getChatMemberByLrzId(charSequence.toString(), new Callback<ChatMember>() {
                        @Override
                        public void onResponse(Call<ChatMember> call, Response<ChatMember> response) {
                            searchView.setError(null);
                            suggestions = new ArrayList<>();
                            suggestions.add(response.body());
                            ((MemberSuggestionsListAdapter)searchView.getAdapter()).updateSuggestions(suggestions);
                        }

                        @Override
                        public void onFailure(Call<ChatMember> call, Throwable t) {
                            onError();
                        }
                    });
                    return;
                }

                boolean containsDigit = false;
                for(int i = 0; i < charSequence.length(); i++){
                    if(Character.isDigit(charSequence.charAt(i))){
                        containsDigit = true;
                        break;
                    }
                }
                if(containsDigit){
                    // don't try to get new suggestions (we don't autocomplete TUM-IDs)
                    if(charSequence.length() > 7){
                        searchView.setError(getString(R.string.error_invalid_tum_id_format));
                    } else {
                        // unfinished TUM-ID
                        searchView.setError(null);
                    }
                    return;
                }

                delayHandler.postDelayed(suggestionRunnable, DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing, we do everything in onTextChanged
            }
        });

    }

    private void getSuggestions(){
        String input = searchView.getText().toString();
        Utils.log("Get suggestions for " + input);
        tumCabeClient.searchChatMember(input, new Callback<List<ChatMember>>() {
            @Override
            public void onResponse(Call<List<ChatMember>> call, Response<List<ChatMember>> response) {
                searchView.setError(null);
                suggestions = response.body();
                ((MemberSuggestionsListAdapter)searchView.getAdapter()).updateSuggestions(suggestions);
            }

            @Override
            public void onFailure(Call<List<ChatMember>> call, Throwable t) {
                onError();
            }
        });
    }

    private void onError(){
        searchView.setError(getString(R.string.error_user_not_found));
    }

    private void showConfirmDialog(ChatMember member){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_action_add_person_blue);
        dialog.setMessage(getString(R.string.add_user_to_chat_message, member.getDisplayName(), chatRoomName));
        dialog.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            joinRoom(member);
            reset();
        });
        dialog.setNegativeButton(R.string.no, null);
        dialog.show();
    }

    /**
     * Clears everything from the last search.
     */
    private void reset(){
        suggestions = new ArrayList<>();
        ((MemberSuggestionsListAdapter)searchView.getAdapter()).updateSuggestions(suggestions);
        searchView.setText("");
    }

    private void joinRoom(ChatMember member){
        ChatVerification verification;
        try {
            ChatMember currentChatMember = Utils.getSetting(this, Const.CHAT_MEMBER, ChatMember.class);
            verification = ChatVerification.Companion.getChatVerification(this, currentChatMember);
        } catch (NoPrivateKey noPrivateKey) {
            Utils.showToast(getBaseContext(), R.string.error);
            return;
        }

        ChatRoom room = new ChatRoom();
        room.setId(roomId);
        room.setName(chatRoomName);
        TUMCabeClient.getInstance(this).addUserToChat(room, member, verification, new Callback<ChatRoom>() {
            @Override
            public void onResponse(Call<ChatRoom> call, Response<ChatRoom> response) {
                ChatRoom room = response.body();
                if(room != null){
                    TcaDb.getInstance(getBaseContext()).chatRoomDao().updateMemberCount(room.getMembers(), room.getId(), room.getName());
                    Utils.showToast(getBaseContext(), R.string.chat_member_added);
                } else {
                    Utils.showToast(getBaseContext(), R.string.error);
                }
            }

            @Override
            public void onFailure(Call<ChatRoom> call, Throwable t) {
                Utils.showToast(getBaseContext(), R.string.error);
            }
        });
    }

}
