package com.elkanah.roemichs.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.model.CreateTestModel;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.elkanah.roemichs.utils.CommonUtils.textOfEditText;
import static com.elkanah.roemichs.utils.CommonUtils.textOfTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTestQuestions extends Fragment implements View.OnClickListener {

    private RadioGroup rgpAnsNo;
    private ArrayList<CreateTestModel> createTestModelArrayList;
    private int index=0;
    private TextView txtBtnNext, txtBtnPrev, txtBtnFinish, txtQuestionNo;
    private EditText edtQuest, edtAnswer1, edtAnswer2, edtAnswer3, edtAnswer4, edtAnswer5;
    private String questnImgUrl, ans1ImgUrl, ans2ImgUrl, ans3ImgUrl, ans4ImgUrl, ans5ImgUrl;
    private LinearLayout lin1, lin2, lin3, lin4, lin5;
    private AutoCompleteTextView spCorrectAnswer;
    private int noOfAnswers=0;
    private Context context;
    private Toolbar toolbar;

    public AddTestQuestions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_test_questions, container, false);
        context = getContext();
        createTestModelArrayList=new ArrayList<>();

        setViewById(view);
        setToolBar();

        txtBtnFinish.setOnClickListener(this);
        txtBtnPrev.setOnClickListener(this);
        txtBtnNext.setOnClickListener(this);

        setUpRadioButton(view);

        return view;
    }

    private void setViewById(View view) {
        rgpAnsNo = view.findViewById(R.id.rdbNumOfAns_AddTestQuest);

        txtBtnFinish = view.findViewById(R.id.tv_btnFinish_addTestQuestion);
        txtBtnNext = view.findViewById(R.id.tv_btnNext_addTestQuest);
        txtBtnPrev = view.findViewById(R.id.tv_btnPrevious_AddTestQuest);

        txtQuestionNo=view.findViewById(R.id.txtCurrentQues_addTestQues);
        edtQuest = view.findViewById(R.id.edtQuestAddTest);
        edtAnswer1 = view.findViewById(R.id.edtAnswer1_addTestQues);
        edtAnswer2 = view.findViewById(R.id.edtAnswer2_addTestQues);
        edtAnswer3 = view.findViewById(R.id.edtAnswer3_addTestQues);
        edtAnswer4 = view.findViewById(R.id.edtAnswer4_addTestQues);
        edtAnswer5 = view.findViewById(R.id.edtAnswer5_addTestQues);

        spCorrectAnswer = view.findViewById(R.id.spCorrectAnswer_addTestQuests);
        lin1 = view.findViewById(R.id.linAns1);
        lin2 = view.findViewById(R.id.linAns2);
        lin3 = view.findViewById(R.id.linAns3);
        lin4 = view.findViewById(R.id.linAns4);
        lin5 = view.findViewById(R.id.linAns5);

        toolbar = view.findViewById(R.id.toolbarAddTestQuest);
    }

    private void setToolBar() {
        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.go_back);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Test Questions");
            setHasOptionsMenu(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();            }
        });
    }

    @Override
    public void onClick(View v) {
        //TODO if all the UI not null
        CreateTestModel createTestModel=new CreateTestModel();
        createTestModel.setQuestionNo(textOfTextView(txtQuestionNo));
        createTestModel.setQuestionText(textOfEditText(edtQuest));
       // createTestModel.setQuestionImgUrl(questnImgUrl);
        createTestModel.setNoOfAnswers(noOfAnswers);
        createTestModel.setAnswer1Text(textOfEditText(edtAnswer1));
     //   createTestModel.setAnswer1ImgUrl(ans1ImgUrl);
        createTestModel.setAnswer2Text(textOfEditText(edtAnswer2));
     //   createTestModel.setAnswer2ImgUrl(ans2ImgUrl);
        createTestModel.setAnswer3Text(textOfEditText(edtAnswer3));
   //     createTestModel.setAnswer3ImgUrl(ans3ImgUrl);
        createTestModel.setAnswer4Text(textOfEditText(edtAnswer4));
    //    createTestModel.setAnswer4ImgUrl(ans4ImgUrl);
        createTestModel.setAnswer5Text(textOfEditText(edtAnswer5));
    //    createTestModel.setAnswer5ImgUrl(ans5ImgUrl);
        createTestModel.setCorrectAnswer(textOfEditText(spCorrectAnswer));
        updateList(createTestModel);

        if(v.getId()== R.id.tv_btnNext_addTestQuest) {
            if(index==createTestModelArrayList.size()){
                index++;
                clearAllFields();
            }
        }else if (v.getId()== R.id.tv_btnPrevious_AddTestQuest){
            if(index!=0){
                index--;
                clearAllFields();
            }
        }else if (v.getId()== R.id.tv_btnFinish_addTestQuestion){
//            ConfirmationDialog dialog = ConfirmationDialog.newInstance("Are you sure you want to submit?", list, answers);
//            dialog.show(getChildFragmentManager(), "message");
//            dialog.setCancelable(false);
        }
    }

    private void updateList(CreateTestModel createTestModel){
        if (createTestModel != null) {
            ArrayList<CreateTestModel> tempArrayList = createTestModelArrayList;
            if (tempArrayList != null) {
                for (CreateTestModel item : tempArrayList) {
                    if (item.getQuestionNo().equals(createTestModel.getQuestionNo())) {
                        setUItoPrev(item);
                    }
                }
            } else {
                createTestModelArrayList.set(index, createTestModel);
            }
        }
    }

    private void setUItoPrev(CreateTestModel model) {
        //use picaso to load their img or just url since it is on their phone
        txtQuestionNo.setText(model.getQuestionNo());
        edtQuest.setText(model.getQuestionText());
        edtAnswer1.setText(model.getAnswer1Text());
        edtAnswer2.setText(model.getAnswer2Text());
        edtAnswer3.setText(model.getAnswer3Text());
        edtAnswer4.setText(model.getAnswer4Text());
        edtAnswer5.setText(model.getAnswer5Text());

        spCorrectAnswer.setText(model.getCorrectAnswer());
    }

    private void clearAllFields() {
        txtQuestionNo.setText("Question "+(index+1));
        edtQuest.setText("");
        edtAnswer1.setText("");
        edtAnswer2.setText("");
        edtAnswer3.setText("");
        edtAnswer4.setText("");
        edtAnswer5.setText("");
        spCorrectAnswer.setText("");
        rgpAnsNo.setSelected(false);
        //set picture to default TODO
    }

    private void setUpRadioButton(View view) {
        rgpAnsNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rgpAnsNo.getCheckedRadioButtonId();
             RadioButton rdbAnsNo = view.findViewById(selectedId);
             noOfAnswers =Integer.parseInt(rdbAnsNo.getText().toString());
     //           String[] correctAns = new String[noOfAnswers];
//                for (int i =0; i<noOfAnswers; i++){
//                    correctAns[i] = "Answer "+(i+1) ;
//                }
                String[] correctAns = {"", "MALE", "FEMALE"};
                ArrayAdapter<String> correctAnsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, correctAns);
                spCorrectAnswer.setAdapter(correctAnsAdapter);
                correctAnsAdapter.notifyDataSetChanged();
                spCorrectAnswer.showDropDown();
                setEdtAnswerVisibility(noOfAnswers);
            }
        });
    }

    private void setEdtAnswerVisibility(int noOfAnswers) {
        switch (noOfAnswers){
            case 2:
                invisibleAnswer(View.INVISIBLE);
                break;
            case 3:
                invisibleAnswer(View.INVISIBLE);
                lin3.setVisibility(View.VISIBLE);
                break;
            case 4:
                invisibleAnswer(View.INVISIBLE);
                lin3.setVisibility(View.VISIBLE);
                lin4.setVisibility(View.VISIBLE);
                break;
            case 5:
                invisibleAnswer(View.VISIBLE);
                break;
        }
    }

    private void invisibleAnswer(int value) {
        lin1.setVisibility(View.VISIBLE);
        lin2.setVisibility(View.VISIBLE);
        lin3.setVisibility(value);
        lin4.setVisibility(value);
        lin5.setVisibility(value);
    }
}
