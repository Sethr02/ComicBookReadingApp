package com.example.comicreader.Adapters;

import static com.example.comicreader.Common.Common.comicSelected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreader.Interface.IRecyclerItemClickListener;
import com.example.comicreader.Model.Character;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class MyCharacterAdapter extends RecyclerView.Adapter<MyCharacterAdapter.MyViewHolder> {

    ImageView characterImage;
    TextView characterTv, characterName, characterCurrentAlias, characterFirstAppearance,
            characterCreators, characterPowersAndMembersLabel, characterPowers, characterDescription;
    Button closeDialog;
    Context context;
    List<Character> characterList;
    LayoutInflater inflater;

    public MyCharacterAdapter(Context context, List<Character> characterList) {
        this.context = context;
        this.characterList = characterList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.character_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.characterName.setText(characterList.get(position).CurrentAlias);

        holder.setRecyclerItemClickListener((view, position1) -> {
            Character character = characterList.get(position1);
            character.setExpandable(!character.isExpandable());
            notifyItemChanged(position1);

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.bottom_sheet_layout, view.findViewById(R.id.bottomSheetContainer));
            characterImage = bottomSheetView.findViewById(R.id.characterImage);
            characterTv = bottomSheetView.findViewById(R.id.character);
            characterName = bottomSheetView.findViewById(R.id.characterName);
            characterCurrentAlias = bottomSheetView.findViewById(R.id.characterCurrentAlias);
            characterFirstAppearance = bottomSheetView.findViewById(R.id.characterFirstAppearance);
            characterCreators = bottomSheetView.findViewById(R.id.characterCreators);
            characterPowersAndMembersLabel = bottomSheetView.findViewById(R.id.characterPowersAndMembersLabel);
            characterPowers = bottomSheetView.findViewById(R.id.characterPowers);
            characterDescription = bottomSheetView.findViewById(R.id.characterDescription);
            closeDialog = bottomSheetView.findViewById(R.id.closeDialog);

            characterTv.setText(characterList.get(position1).CurrentAlias);

            if (characterList.get(position1).Image.isEmpty()) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/comic-book-app-12d12.appspot.com/o/marvel.jpg?alt=media&token=800c62cd-5d82-40d5-b421-4f526ac6ec96").into(characterImage);
            } else {
                Picasso.get().load(characterList.get(position1).Image).into(characterImage);
            }

            characterName.setText(characterList.get(position1).Name);

            characterCurrentAlias.setText(characterList.get(position1).CurrentAlias);

            if (characterList.get(position1).FirstAppearance.isEmpty())
                characterFirstAppearance.setText(new StringBuilder("N/A"));
            else
                characterFirstAppearance.setText(characterList.get(position1).FirstAppearance);

            if (characterList.get(position1).Creators.isEmpty())
                characterCreators.setText(new StringBuilder("N/A"));
            else
                characterCreators.setText(characterList.get(position1).Creators);

            if (characterList.get(position1).Powers.isEmpty()) {
                characterPowersAndMembersLabel.setText("Members");
                characterPowers.setText(characterList.get(position1).Members);
            } else {
                characterPowersAndMembersLabel.setText("Powers");
                characterPowers.setText(characterList.get(position1).Powers);
            }

            characterDescription.setText(characterList.get(position1).Description);

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            closeDialog.setOnClickListener(view1 -> bottomSheetDialog.hide());
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView characterName;

        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            characterName = itemView.findViewById(R.id.characterName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
