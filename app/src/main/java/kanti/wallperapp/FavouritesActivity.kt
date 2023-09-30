package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_favourites)
	}

	companion object {

		fun startActivity(context: Context) {
			val intent = Intent(context, FavouritesActivity::class.java)
			context.startActivity(intent)
		}

	}

}