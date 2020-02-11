package com.example.todokevin.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todokevin.R
import com.example.todokevin.tasklist.Task
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        validate_button.setOnClickListener {
            val createTask = Task(id = UUID.randomUUID().toString(), title = edit_title.text.toString(), description = edit_description.text.toString())
            intent.putExtra(ADD_TASK_REPLY_CODE.toString(), createTask)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val ADD_TASK_REPLY_CODE = 666
    }
}
