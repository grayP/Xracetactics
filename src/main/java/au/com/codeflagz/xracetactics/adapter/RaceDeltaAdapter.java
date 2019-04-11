package au.com.codeflagz.xracetactics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.model.Handicap;

public class RaceDeltaAdapter extends ListAdapter<Handicap, RaceDeltaAdapter.HandicapHolder> {
    // private List<Handicap> handicaps = new ArrayList<>();
    private onItemClickListener listener;
    private int startHour;
    private int startMinute;
    private double benchRating;

    public RaceDeltaAdapter(int hour, int minute, double benchRating) {
        super(DIFF_CALLBACK);
        this.startMinute = minute;
        this.startHour = hour;
        this.benchRating=benchRating;
    }

    private static final DiffUtil.ItemCallback<Handicap> DIFF_CALLBACK = new DiffUtil.ItemCallback<Handicap>() {
        @Override
        public boolean areItemsTheSame(@NonNull Handicap oldItem, @NonNull Handicap newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Handicap oldItem, @NonNull Handicap newItem) {
            return oldItem.getId() == newItem.getId()
                    && oldItem.getBoat() == newItem.getBoat()
                    && oldItem.getRace() == newItem.getRace()
                    && oldItem.getRating() == newItem.getRating();
        }
    };

    @NonNull
    @Override
    public HandicapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.race_delta_item, parent, false);

        return new HandicapHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull HandicapHolder holder, int position) {
        Handicap currentHandicap = getItem(position);
        holder.textBoat.setText(currentHandicap.getBoat());
        holder.textRating.setText(String.format("%.2f", currentHandicap.getRating()));
        holder.textDelta.setText( currentHandicap.getDelta(startHour, startMinute, benchRating));
    }

    public Handicap getHandicapAt(int position) {
        return getItem(position);
    }

    class HandicapHolder extends RecyclerView.ViewHolder {
        private TextView textBoat;
        private TextView textRating;
        private TextView textDelta;

        public HandicapHolder(@NonNull View itemView) {
            super(itemView);
            textBoat = itemView.findViewById(R.id.text_view_boat);
            textRating = itemView.findViewById(R.id.text_view_rating);
            textDelta = itemView.findViewById(R.id.text_delta);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
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
