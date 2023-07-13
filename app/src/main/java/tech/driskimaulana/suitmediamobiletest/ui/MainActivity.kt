package tech.driskimaulana.suitmediamobiletest.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import tech.driskimaulana.suitmediamobiletest.R
import tech.driskimaulana.suitmediamobiletest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        initView();
    }

    private fun initView() {

        binding.btnCheck.setOnClickListener {
            handleCheckButton();
        }

        binding.btnNext.setOnClickListener {
            handleNextButton()
        }
    }

    private fun handleNextButton() {
        val intent = Intent(this, SecondScreeenActivity::class.java);
        val name = binding.etName.text.toString();
        if (name.length == 0) {
            Toast.makeText(this, "Please input your name.", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("name", name);
        startActivity(intent);
    }

    private fun handleCheckButton() {
        val textToCheck = binding.etCheck.text.toString().filter { !it.isWhitespace() };

        if (textToCheck.length == 0) {
            Toast.makeText(this, "Please input some words to check.", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog(isPalindrome(textToCheck));
    }
    
    private fun isPalindrome(textToCheck: String) : Boolean {
        return textToCheck == textToCheck.reversed();
    }

    private fun showDialog(isPalindrome: Boolean) {
        val dialog = Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        val dialogBody = dialog.findViewById<TextView>(R.id.tvBody);
        dialogBody.text = if(isPalindrome) "IsPalindrome" else "Not Palindrome";
        val okeButton = dialog.findViewById<Button>(R.id.btn_oke);

        okeButton.setOnClickListener {
            dialog.dismiss();
        }

        dialog.show();

    }
}