package au.com.codeflagz.xracetactics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.model.Handicap;

public class HandicapAdapter extends ListAdapter<Handicap, HandicapAdapter.HandicapHolder> {
   // private List<Handicap> handicaps = new ArrayList<>();
    private onItemClickListener listener;

    public HandicapAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Handicap> DIFF_CALLBACK = new DiffUtil.ItemCallback<Handicap>() {
        @Override
        public boolean areItemsTheSame(@NonNull Handicap oldItem, @NonNull Handicap newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Handicap oldItem, @NonNull Handicap newItem) {
            return oldItem.getId()==newItem.getId()
                    && oldItem.getBoat()==newItem.getBoat()
                    && oldItem.getRace()==newItem.getRace()
                    && oldItem.getRating()==newItem.getRating();
        }
    };

    @NonNull
    @Override
    public HandicapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.handicap_item, parent, false);

        return new HandicapHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull HandicapHolder holder, int position) {
        Handicap currentHandicap = getItem(position);
        holder.textBoat.setText(currentHandicap.getBoat());
        holder.textRating.setText(String.format("%.2f", currentHandicap.getRating()));
        holder.textRace.setText(String.valueOf(currentHandicap.getRace()));
    }

// @Override
//    public int getItemCount() {
//        return handicaps.size();
//    }
//
//    public void setHandicaps(List<Handicap> handicaps) {
//        this.handicaps = handicaps;
//        this.notifyDataSetChanged();
//    }

    public Handicap getHandicapAt(int position) {
        return getItem(position);
    }

    class HandicapHolder extends RecyclerView.ViewHolder {
        private TextView textBoat;
        private TextView textRating;
        private TextView textRace;

        public HandicapHolder(@NonNull View itemView) {
            super(itemView);
            textBoat = itemView.findViewById(R.id.text_view_boat);
            textRating = itemView.findViewById(R.id.text_view_rating);
            textRace = itemView.findViewById(R.id.text_view_race);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Handicap handicap);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
