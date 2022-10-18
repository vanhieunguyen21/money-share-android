package com.example.moneyshare.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.moneyshare.constant.Constant
import com.example.moneyshare.constant.getGroupProfileImageLink
import com.example.moneyshare.domain.model.Group
import com.skydoves.landscapist.glide.GlideImage
import com.example.moneyshare.R
import com.skydoves.landscapist.ImageOptions

@Composable
fun GroupItem(
    group: Group,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = 70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = {
                if (group.profileImageUrl != null)
                    getGroupProfileImageLink(group.profileImageUrl)
                else R.drawable.default_group_profile_image
            },
            modifier = Modifier
                .padding(end = 12.dp)
                .size(50.dp)
                .clip(CircleShape)
//                .border(1.dp, MaterialTheme.colors.onPrimary, CircleShape)
                .align(Alignment.CenterVertically),
            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
        )
        Text(
            text = group.name,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h6
        )
    }
}