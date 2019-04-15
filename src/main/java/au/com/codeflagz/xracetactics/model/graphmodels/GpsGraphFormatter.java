package au.com.codeflagz.xracetactics.model.graphmodels;

import android.graphics.Color;
import android.util.Log;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GpsGraphFormatter {

    public GraphView gpsGraphImage;
    private LineGraphSeries<DataPoint> sogSeries= new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> cogSeries= new LineGraphSeries<>();

    private double LastYValue;

    public GpsGraphFormatter() {
        LastYValue = 0;
    }


    private void SetupSeries(LineGraphSeries<DataPoint> series, int colour, int size, String title) {
        series.setThickness(size);
        series.setDrawDataPoints(false);
        series.setColor(colour);
        series.setTitle(title);
    }

    public void SetupGraph() {
        SetupSeries(sogSeries, Color.RED, 2, "Sog-Slow");
        SetupSeries(cogSeries, Color.BLACK, 2, "Cog-Slow");
        gpsGraphImage.addSeries(cogSeries);
        gpsGraphImage.getSecondScale().addSeries(sogSeries);
        gpsGraphImage.getSecondScale().setMinY(0);
        gpsGraphImage.getSecondScale().setMaxY(10);
        gpsGraphImage.getSecondScale().setVerticalAxisTitleColor(android.R.color.holo_red_dark);
        gpsGraphImage = setGraph(gpsGraphImage, "Cog");
    }

    private GraphView setGraph(GraphView graph, String title) {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getGridLabelRenderer().setNumVerticalLabels(8);
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
        final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.SECOND, -180);
        Date d2 = calendar.getTime();
        graph.getViewport().setMaxX(d1.getTime());
        graph.getViewport().setMinX(d2.getTime());
        graph.getGridLabelRenderer().setHumanRounding(true);
        graph.getGridLabelRenderer().setPadding(50);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumIntegerDigits(1);
        graph.getSecondScale().setLabelFormatter(new DefaultLabelFormatter(nf, nf));

        return graph;
    }


    private void SetYAxis(double LastValue) {
        if (Math.abs(LastYValue - LastValue) > 15.0) {
            LastYValue = LastValue;
            double minY = 10 * Math.floor(LastValue / 10) - 20;
            double maxY = 10 * Math.ceil(LastValue / 10) + 20;
            gpsGraphImage.getViewport().setMinY((int) minY);
            gpsGraphImage.getViewport().setMaxY((int) maxY);
            int NumBars = (int) ((maxY - minY) / 5 + 1);
            gpsGraphImage.getGridLabelRenderer().setNumVerticalLabels(NumBars);
            gpsGraphImage.getViewport().setYAxisBoundsManual(true);
            gpsGraphImage.onDataChanged(true, true);
        }
    }

    public void AddCogDataAndSetYAxis(double value, long timeOfReading, int numreadings) {
        cogSeries.appendData(new DataPoint(timeOfReading, value), true, numreadings);
        Log.d("Cog value", "AddCogDataAndSetYAxis: " + value);
        SetYAxis(value);
    }


    public void UpdateSeries(GpsDataPoint point) {
        AddCogDataAndSetYAxis(point.getCog(), point.getTimeofReading(), 100);
        AddSogDataAndSetYAxiz(point.getSog(), point.getTimeofReading(), 100);
    }

    private void AddSogDataAndSetYAxiz(double sog, long timeofReading, int i) {
        sogSeries.appendData(new DataPoint(timeofReading, sog), true, 100);
        gpsGraphImage.getSecondScale().setMinY(Math.max(0, Math.floor(sog - 2)));
        gpsGraphImage.getSecondScale().setMaxY(Math.floor(sog + 2));

    }
}
