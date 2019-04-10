package au.com.codeflagz.xracetactics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import au.com.codeflagz.xracetactics.R;
import au.com.codeflagz.xracetactics.model.Race;

public class RaceAdapter extends ListAdapter<Race, RaceAdapter.RaceHolder> {
    // private List<Race> races = new ArrayList<>();
    private onItemClickListener listener;

    public RaceAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Race> DIFF_CALLBACK = new DiffUtil.ItemCallback<Race>() {
        @Override
        public boolean areItemsTheSame(@NonNull Race oldItem, @NonNull Race newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Race oldItem, @NonNull Race newItem) {
            return oldItem.getId() == newItem.getId()
                    && oldItem.getStartHour() == newItem.getStartHour()
                    && oldItem.getStartMinute() == newItem.getStartMinute()
                    && oldItem.getRace() == newItem.getRace()
                    && oldItem.getRating() == newItem.getRating();
        }
    };

    @NonNull
    @Override
    public RaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.race_item, parent, false);

        return new RaceHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RaceHolder holder, int position) {
        Race currentRace = getItem(position);
        holder.textRace.setText(String.valueOf(currentRace.getRace()));
        holder.textRating.setText(String.format("%.2f", currentRace.getRating()));
        holder.textStartTime.setText(String.valueOf(currentRace.getStartHour()) + ":" + String.valueOf(currentRace.getStartMinute()));
    }

// @Override
//    public int getItemCount() {
//        return races.size();
//    }
//
//    public void setRaces(List<Race> races) {
//        this.races = races;
//        this.notifyDataSetChanged();
//    }

    public Race getRaceAt(int position) {
        return getItem(position);
    }

    class RaceHolder extends RecyclerView.ViewHolder {
        private TextView textRace;
        private TextView textRating;
        private TextView textStartTime;
        private Button buttonEdit;

        public RaceHolder(@NonNull View itemView) {
            super(itemView);
            textRace = itemView.findViewById(R.id.text_view_race);
            textRating = itemView.findViewById(R.id.text_view_rating);
            textStartTime = itemView.findViewById(R.id.text_view_start_time);


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
        void onItemClick(Race race);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
