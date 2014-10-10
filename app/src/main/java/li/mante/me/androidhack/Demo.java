package li.mante.me.androidhack;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.wearable.view.WatchViewStub;
import android.view.SurfaceView;
import android.widget.TextView;

public class Demo extends Activity implements GestureOverlayView.OnGesturePerformedListener  {

    private GestureOverlayView gestures = null;
    private TextView text = null;
    private SurfaceView surface = null;

/*    private GoogleApiClient communication  = new GoogleApiClient.Builder(this)
            .addApi(Wearable.API)
            .build();
            */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // fix issues with threads

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        final GestureOverlayView.OnGesturePerformedListener gestureOK = this;

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                gestures = (GestureOverlayView) stub.findViewById(R.id.gestures);
                text = (TextView) stub.findViewById( R.id.text );

                gestures.addOnGesturePerformedListener( gestureOK );

                // surface = (SurfaceView) stub.findViewById( R.id.surface );
            }
        });

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        Bitmap bitmap = gesture.toBitmap( overlay.getWidth(), overlay.getHeight() , 100, 100);


        text.setText( "Wait..." );

        AsyncTask t = new SendStuff().execute(bitmap);

        // Canvas temp = new Canvas( bitmap );
        // surface.draw( temp );


    }


    class SendStuff extends AsyncTask<Bitmap, Void, String> {

        private Exception exception;

        protected String doInBackground(Bitmap... bitmap) {

            /*
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(communication).await();

            String id = nodes.getNodes().get(0).getId();

            Bitmap image = bitmap[0];

            int[] data = new int[ image.getByteCount() ];

            image.getPixels( data, 0, 0, 0, 0, image.getWidth(), image.getHeight() );

            byte[] data2 = new byte[ data.length ];
            for( int i = 0; i < data.length; i++ ) {
                data2[i] = (byte) data[i];
            }


            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage( communication, id, "demo", data2 ).await();
            */

            return "OK";
        }

        @Override
        protected void onPostExecute(String s) {
            text.setText("Done :) Draw new a shape");
        }
    }


}

