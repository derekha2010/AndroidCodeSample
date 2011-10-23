package com.halo.touchTest;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.widget.Toast;
import com.halo.GestureWithFling.R;

public class GestureWithFlingActivity extends Activity implements
		OnGesturePerformedListener, View.OnTouchListener {
	
	GestureLibrary gesturelib = null;
	GestureDetector gestureDetector;
	View onTouchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gesturelib = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (gesturelib.load()) {
			GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.gestureOverlay);
			gestureView.addOnGesturePerformedListener(this);
		}
		gestureDetector = new GestureDetector(new MyGestureDetector());
		findViewById(R.id.tv1).setOnTouchListener(this);
		findViewById(R.id.tv2).setOnTouchListener(this);
		findViewById(R.id.tv3).setOnTouchListener(this);
		findViewById(R.id.tv4).setOnTouchListener(this);

	}

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		if (gesture.getLength() > 500) {
			ArrayList<Prediction> predictions = gesturelib.recognize(gesture);
			if (predictions.size() > 0) {
				Prediction prediction = (Prediction) predictions.get(0);
				if (prediction.score > 1.0) {
					Toast.makeText(GestureWithFlingActivity.this, "Circle",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private static final int SWIPE_MIN_DISTANCE = 60;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (((GestureOverlayView) findViewById(R.id.gestureOverlay))
					.getGesture().getLength() < 400)
				try {
					if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MAX_OFF_PATH) {
						// right to left swipe
						if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
							Toast.makeText(GestureWithFlingActivity.this,
									onTouchView.getTag() + " Right to left",
									Toast.LENGTH_SHORT).show();
							return true;
						} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
								&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
							Toast.makeText(GestureWithFlingActivity.this,
									onTouchView.getTag() + " Left to right",
									Toast.LENGTH_SHORT).show();
							return true;
						}
					}
					if (Math.abs(e1.getX() - e2.getX()) < SWIPE_MAX_OFF_PATH) {
						// bottom to top swipe
						if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
								&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
							Toast.makeText(GestureWithFlingActivity.this,
									onTouchView.getTag() + " Bottom to top",
									Toast.LENGTH_SHORT).show();
							return true;
						} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
								&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
							Toast.makeText(GestureWithFlingActivity.this,
									onTouchView.getTag() + " Top to bottom",
									Toast.LENGTH_SHORT).show();
							return true;
						}
					}

				} catch (Exception e) {
					// nothing
				}
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (((GestureOverlayView) findViewById(R.id.gestureOverlay))
					.getGesture().getLength() < 10)
				Toast.makeText(GestureWithFlingActivity.this,
						onTouchView.getTag() + " Clicked", Toast.LENGTH_SHORT)
						.show();
			return true;
		}

	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		onTouchView = view;
		if (gestureDetector.onTouchEvent(event)) {
			return true;
		}
		return false;
	}

}