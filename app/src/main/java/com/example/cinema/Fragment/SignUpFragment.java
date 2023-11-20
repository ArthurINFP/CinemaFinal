package com.example.cinema.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cinema.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText edtEmail,edtPassword,edtAgain;
    private Button btnRegister;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtEmail = view.findViewById(R.id.signup_email);
        edtPassword = view.findViewById(R.id.signup_password);
        edtAgain = view.findViewById(R.id.signup_confirm);
        btnRegister = view.findViewById(R.id.signup_button);
        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtEmail.setBackground(getResources().getDrawable(R.drawable.edittext_bkg));
                }
            }
        });
        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtPassword.setBackground(getResources().getDrawable(R.drawable.edittext_bkg));
                }
            }
        });
        edtAgain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtAgain.setBackground(getResources().getDrawable(R.drawable.edittext_bkg));
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditText()) {
                    checkUser(edtEmail.getText().toString(), edtPassword.getText().toString());
                }
            }
        });
    }

    private void checkUser(String email, String password) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.img);
        dialog.setTitle("Checking Authentication");
        dialog.setMessage("loading");
        dialog.show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            dialog.dismiss();
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            dialog.dismiss();
                            Log.w("register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Invalid user, please check your email or password"
                                    , Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private boolean checkEditText() {
        boolean flag = true;

        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setBackgroundColor(getResources().getColor(R.color.md_theme_light_error));
            flag = false;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setBackgroundColor(getResources().getColor(R.color.md_theme_light_error));
            flag = false;
        }
        if (edtAgain.getText().toString().isEmpty()){
            edtAgain.setBackgroundColor(getResources().getColor(R.color.md_theme_light_error));
            flag = false;
        }
        if (!edtPassword.getText().toString().equals(edtAgain.getText().toString())){
            edtAgain.setBackgroundColor(getResources().getColor(R.color.md_theme_light_error));
            Toast.makeText(getContext(), "The password is incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!flag) {
            Toast.makeText(getContext(), "Please fill the fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
}