package com.textfileencryptor;

import com.textfileencryptor.SplachScreenActivity;
import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends Activity {
	
	public final int REQ_CD_FILEPICKER = 101;
	
	private ArrayList<String> listString = new ArrayList<>();
	
	private LinearLayout linear2;
	private Button button3;
	private LinearLayout linear1;
	private TextView developed_by_abnsafita;
	private EditText edittext1;
	private TextView textview1;
	private Button button1;
	private Button button2;
	
	private Intent filePicker = new Intent(Intent.ACTION_GET_CONTENT);
	private AlertDialog.Builder B01_Dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
			||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		button3 = findViewById(R.id.button3);
		linear1 = findViewById(R.id.linear1);
		developed_by_abnsafita = findViewById(R.id.developed_by_abnsafita);
		edittext1 = findViewById(R.id.edittext1);
		textview1 = findViewById(R.id.textview1);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		filePicker.setType("*/*");
		filePicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		B01_Dialog = new AlertDialog.Builder(this);
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(filePicker, REQ_CD_FILEPICKER);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ("".equals(edittext1.getText().toString())) {
					((EditText)edittext1).setError("كلمة السر");
				}
				else {
					if (20 > textview1.getText().toString().length()) {
						ApplicationUtil.showMessage(getApplicationContext(), "يرجى اختيار ملف");
					}
					else {
						FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/AES-256/Encrypted/"));
						_AES256FileEncryption(textview1.getText().toString(), FileUtil.getExternalStorageDir().concat("/AES-256/Encrypted/".concat(Uri.parse(textview1.getText().toString()).getLastPathSegment())), edittext1.getText().toString());
						ApplicationUtil.showMessage(getApplicationContext(), FileUtil.getExternalStorageDir().concat("/AES-256/Encrypted/".concat(Uri.parse(textview1.getText().toString()).getLastPathSegment())));
					}
				}
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ("".equals(edittext1.getText().toString())) {
					((EditText)edittext1).setError("كلمة السر");
				}
				else {
					if (20 > textview1.getText().toString().length()) {
						ApplicationUtil.showMessage(getApplicationContext(), "يرجى اختيار ملف");
					}
					else {
						FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/AES-256/Decrypted/"));
						_AES256FileDecryption(textview1.getText().toString(), FileUtil.getExternalStorageDir().concat("/AES-256/Decrypted/".concat(Uri.parse(textview1.getText().toString()).getLastPathSegment())), edittext1.getText().toString());
						ApplicationUtil.showMessage(getApplicationContext(), FileUtil.getExternalStorageDir().concat("/AES-256/Decrypted/".concat(Uri.parse(textview1.getText().toString()).getLastPathSegment())));
					}
				}
			}
		});
	}
	
	private void initializeLogic() {
		
		_Gradient_Text(developed_by_abnsafita, "Programmed by Ali");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FILEPICKER:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				textview1.setText(_filePath.get((int)(0)));
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		B01_Dialog.setTitle("اغلاق التطبيق");
		B01_Dialog.setIcon(R.drawable.exit_b01);
		B01_Dialog.setMessage("هل تريد الخروج من التطبيق؟");
		B01_Dialog.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
						ApplicationUtil.CustomToastWithIcon(getApplicationContext(), "Exit !!!", 0xFF00FF00, 20, 0x33000000, 15, ApplicationUtil.BOTTOM, R.drawable.exit_b01);
						finishAffinity();
				}
		});
		B01_Dialog.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
						ApplicationUtil.CustomToastWithIcon(getApplicationContext(), "Cancel !!!", 0xFFFF0000, 20, 0x33000000, 15, ApplicationUtil.BOTTOM, R.drawable.cancel_b01);
				}
		});
		B01_Dialog.create().show();
	}
	public void _AES256FileEncryption(final String _input, final String _output, final String _password) {
		FileEncryption.encryptFile(_input, _output, _password);
	}
	
	
	public void _AES256FileDecryption(final String _input, final String _output, final String _password) {
		FileEncryption.decryptFile(_input, _output, _password);
	}
	
	
	public void _Gradient_Text(final TextView _view, final String _msg) {
		_view.setText(_msg);
		
		TextPaint paint = _view.getPaint();
		
		float width = paint.measureText(_msg); 
		
		Shader textShader = new LinearGradient(0, 0, width,_view.getTextSize(), new int[]{ Color.parseColor("#F97C3C"), Color.parseColor("#FDB54E"), Color.parseColor("#64B678"), Color.parseColor("#478AEA"), Color.parseColor("#8446CC"), }, null, Shader.TileMode.CLAMP); 
		
		/* Programmed by Ali To request help, contact me on Telegram: @ali_r_1997
 */
		
		_view.getPaint().setShader(textShader);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
