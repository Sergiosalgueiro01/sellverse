package com.main.es.sellverse.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.R;
import com.main.es.sellverse.persistence.UserDataBase;
import com.main.es.sellverse.util.datasavers.TemporalUserSaver;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Chat> chats;
    Context context;
    LayoutInflater inflater;

    public ChatAdapter(Context context, List<Chat> chats){
        this.context = context;
        this.chats = chats;
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nombreMensaje;
        final TextView mensajeMensaje;
        final CircleImageView image;
        final TextView horaMensaje;

        public ViewHolder(View view) {
            super(view);
            this.nombreMensaje = view.findViewById(R.id.nombreMensaje);
            this.mensajeMensaje =  view.findViewById(R.id.mensajeMensaje);
            this.image =  view.findViewById(R.id.fotoPerfilMensaje);
            this.horaMensaje = view.findViewById(R.id.horaMensaje);
            // Define click listener for the ViewHolder's View

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_cardview, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Chat chat = chats.get(position);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userID.equals(chat.getBuyerId())){
            UserDataBase.getUserById(chat.getSellerId());
        }else{
            UserDataBase.getUserById(chat.getBuyerId());
        }
        User u = TemporalUserSaver.getInstance().user;

        viewHolder.nombreMensaje.setText(u.getEmail());
        ChatMessage last = chat.getMessages().get(chat.getMessages().size()-1);
        viewHolder.horaMensaje.setText(last.getTime().getHours() + ":" + last.getTime().getMinutes());
        viewHolder.mensajeMensaje.setText(chat.getLastMessage());
        viewHolder.image.setImageResource(R.drawable.logo_martillo);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return chats.size();
    }
}
