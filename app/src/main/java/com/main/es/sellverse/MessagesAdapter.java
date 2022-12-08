package com.main.es.sellverse;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.main.es.sellverse.model.ChatMessage;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>{

    private List<ChatMessage> listMessages;

    public MessagesAdapter(List<ChatMessage> messages){
        this.listMessages = messages;
    }

    /* Indicamos el layout a "inflar" para usar en la vista
     */
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        System.out.println("POR AQUI LLEGA");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_cardview, parent, false);
        return new MessageViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        ChatMessage message = listMessages.get(position);
        Log.i("Lista","Visualiza elemento: "+ message);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindMessage(message);
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }

    /*Clase interna que define los compoonentes de la vista*/

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        final TextView nombreMensaje;
        final TextView mensajeMensaje;
        final CircleImageView image;
        final TextView horaMensaje;
        final CardView cardView;

        public MessageViewHolder(View itemView) {
            super(itemView);

            this.nombreMensaje = itemView.findViewById(R.id.nombreMensaje);
            this.mensajeMensaje =  itemView.findViewById(R.id.mensajeMensaje);
            this.image =  itemView.findViewById(R.id.fotoPerfilMensaje);
            this.horaMensaje = itemView.findViewById(R.id.horaMensaje);
            this.cardView = itemView.findViewById(R.id.chatContainer);
        }

        // asignar valores a los componentes
        public void bindMessage(final ChatMessage message) {
            nombreMensaje.setText(message.getUserName());
            mensajeMensaje.setText(message.getText());
            image.setImageResource(R.drawable.logo_martillo);



            String hour = message.getTime().getHours() + "";
            String minutes = message.getTime().getMinutes() + "";
            if(hour.length() == 1)
                hour = "0"+hour;
            if(minutes.length() == 1)
                minutes = "0"+minutes;
            horaMensaje.setText( hour + ":" + minutes);



        }
    }
}
