package com.note.note.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.note.note.R
import com.note.note.components.NoteButton
import com.note.note.components.NoteInputText
import com.note.note.data.NoteDataSource
import com.note.note.model.Note
import com.note.note.util.formatDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
){
    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }
    
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                Icon(imageVector = Icons.Rounded.Notifications , contentDescription = "Icon")
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFDADFE3)
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if(it.all { char ->
                            char.isLetter() || char.isWhitespace()  // isLetter() 알파벳인지, isWhitespace() 공백인지
                    }) {
                        title = it
                    }
                }
            )

            NoteInputText(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if(it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) {
                        description = it
                    }
                }
            )

            NoteButton(
                text = "Save",
                onClick = {
                    if(title.isNotEmpty() && description.isNotEmpty()){
                        onAddNote(
                            Note(title = title, description = description)
                        )
                        title = ""
                        description = ""
                        Toast.makeText(context, "Noted Added", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        Divider(modifier = Modifier.padding(12.dp))
        LazyColumn{
            items(notes){note ->
                NoteRow(
                    note = note,
                    onNoteClicked = {
                        onRemoveNote(note)
                    }
                )
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
){
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 32.dp, bottomStart = 32.dp))
            .fillMaxWidth(),
        color = Color(0xFFdFE6EB),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier
                .clickable { onNoteClicked(note) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview(){
    NoteScreen(
        notes = NoteDataSource().loadNotes(),
        onAddNote = {},
        onRemoveNote = {}
    )
}