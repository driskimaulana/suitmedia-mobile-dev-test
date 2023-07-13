package tech.driskimaulana.suitmediamobiletest.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.driskimaulana.suitmediamobiletest.data.models.RegresinModel
import tech.driskimaulana.suitmediamobiletest.data.remote.response.UsersResponse
import tech.driskimaulana.suitmediamobiletest.data.remote.retrofit.ApiConfig
import tech.driskimaulana.suitmediamobiletest.data.remote.retrofit.ApiService
import tech.driskimaulana.suitmediamobiletest.databinding.ActivityThirdScreenBinding
import tech.driskimaulana.suitmediamobiletest.interefaces.OnBackPressedListener
import tech.driskimaulana.suitmediamobiletest.ui.adapters.RecViewAdapter


class ThirdScreenActivity : AppCompatActivity(), OnBackPressedListener {

    private lateinit var binding: ActivityThirdScreenBinding;

    private val items = arrayListOf<RegresinModel?>();

    private lateinit var recyclerViewAdapter: RecViewAdapter;

    private var page = 1;

    private var isLoading = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.midlleLoading.visibility = View.VISIBLE;

        populateData();

        initView();
        initAdapter();
        initScrollListener();
    }

    private fun initView(){
        supportActionBar?.title = "Third Screen";
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                super.onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun populateData() {

        val call = ApiConfig.getApiService().getUsers(page);
        call.enqueue(object: Callback<UsersResponse>{

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(applicationContext, "Failed! Error while fetching data.", Toast.LENGTH_SHORT).show();
            }

            override fun onResponse(
                call: Call<UsersResponse>,
                response: retrofit2.Response<UsersResponse>
            ) {
                if (response.body()?.data?.size == 0  ) {
                    Toast.makeText(applicationContext, "You are in the last page.", Toast.LENGTH_SHORT).show();
                }

                for (i in response.body()?.data!!) {
                    items.add(i);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
                isLoading = false;
                page++;
                binding.midlleLoading.visibility = View.GONE;
                binding.loadLoading.visibility = View.GONE;
                binding.tvLoading.visibility = View.GONE;
            }
        })

    }

    private fun initAdapter() {
        recyclerViewAdapter = RecViewAdapter(items, this)
        binding.recView.adapter =recyclerViewAdapter

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        ContextCompat.getDrawable(this, tech.driskimaulana.suitmediamobiletest.R.drawable.divider)
            ?.let { dividerItemDecoration.setDrawable(it) }
        binding.recView.addItemDecoration(dividerItemDecoration);
    }

    private fun initScrollListener() {
        binding.recView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == items.size - 1) {
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        binding.loadLoading.visibility = View.VISIBLE;
        binding.tvLoading.visibility = View.VISIBLE;
        populateData()
        isLoading = false
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onBackPressedWithData(name: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("result_key", name)
        setResult(RESULT_OK, resultIntent)
        super.onBackPressed()
    }

}