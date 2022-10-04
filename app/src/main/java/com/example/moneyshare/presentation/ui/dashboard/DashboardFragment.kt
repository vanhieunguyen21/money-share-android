package com.example.moneyshare.presentation.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import com.example.moneyshare.R
import com.example.moneyshare.presentation.components.GroupDashboard
import com.example.moneyshare.presentation.components.GroupSummary
import com.example.moneyshare.presentation.theme.AppTheme
import com.skydoves.landscapist.glide.GlideImage

class DashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    Scaffold(
                        topBar = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                                    .height(60.dp)
                                    .padding(8.dp),
                            ) {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    Icon(Icons.Filled.Menu, null, modifier = Modifier.size(30.dp))
                                }
                                Text(
                                    text = stringResource(R.string.dashboard),
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 8.dp)
                                )
                                Spacer(Modifier.weight(1f))
                                GlideImage(
                                    imageModel = ImageBitmap
                                        .imageResource(id = R.drawable.empty_plate)
                                        .asAndroidBitmap(),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Color.Black, CircleShape)
                                        .align(Alignment.CenterVertically),
                                )
                            }
                        },
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background)
                                .padding(it)
                        ) {
                            val isPortrait = maxWidth < maxHeight
                            if (isPortrait) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colors.background)
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                                            .weight(3f),
                                        elevation = 8.dp
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Red)
                                                .padding(8.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "Overall balance",
                                                style = MaterialTheme.typography.subtitle1,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                            Text(
                                                text = "-$256",
                                                style = MaterialTheme.typography.h5,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }


                                    GroupDashboard(
                                        listOf(
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .weight(6f)
                                    )
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colors.background)
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .weight(4f),
                                        elevation = 8.dp
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Red)
                                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "Overall balance",
                                                style = MaterialTheme.typography.subtitle1,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                            Text(
                                                text = "-$256",
                                                style = MaterialTheme.typography.h5,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }

                                    GroupDashboard(
                                        listOf(
                                        ),
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(8.dp)
                                            .weight(7f)
                                    )
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}