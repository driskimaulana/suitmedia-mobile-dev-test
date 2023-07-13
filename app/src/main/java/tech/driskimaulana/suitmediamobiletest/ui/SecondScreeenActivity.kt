package tech.driskimaulana.suitmediamobiletest.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import tech.driskimaulana.suitmediamobiletest.databinding.ActivitySecondScreeenBinding


class SecondScreeenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreeenBinding;
    private lateinit var name: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreeenBinding.inflate(layoutInflater);
        setContentView(binding.root);

        initView()
    }

    private fun initView() {
        name = intent.getStringExtra("name").toString();
        binding.tvName.text = name;

        supportActionBar?.title = "Second Screen";
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        binding.btnChooseUser.setOnClickListener {
            handleChooseUser();
        }

    }

    private fun handleChooseUser() {
        val intent = Intent(this, ThirdScreenActivity::class.java);
        startActivityForResult(intent, 1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                super.onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("driskidebug", "1")
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val result = data.getStringExtra("result_key")
            binding.tvChoosenUser.text = result.toString();
        }
    }
}