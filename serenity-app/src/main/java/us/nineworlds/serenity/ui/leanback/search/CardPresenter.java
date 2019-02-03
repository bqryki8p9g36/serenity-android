/**
 * The MIT License (MIT)
 * Copyright (c) 2012 David Carver
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package us.nineworlds.serenity.ui.leanback.search;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import toothpick.Toothpick;
import us.nineworlds.serenity.R;
import us.nineworlds.serenity.common.annotations.InjectionConstants;
import us.nineworlds.serenity.core.model.VideoContentInfo;
import us.nineworlds.serenity.ui.util.ImageUtils;

public class CardPresenter extends Presenter {

  Context context;

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent) {
    Toothpick.inject(this, Toothpick.openScope(InjectionConstants.APPLICATION_SCOPE));
    context = parent.getContext();

    ImageCardView imageView = createImageView();
    imageView.setFocusable(true);
    imageView.setFocusableInTouchMode(true);
    imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.holo_color));
    return new CardPresenterViewHolder(imageView);
  }

  @NonNull
  protected ImageCardView createImageView() {
    return new ImageCardView(context);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, Object item) {
    VideoContentInfo video = (VideoContentInfo) item;

    CardPresenterViewHolder cardHolder = (CardPresenterViewHolder) viewHolder;
    ImageCardView imageCardView = cardHolder.getCardView();
    cardHolder.setMovie(video);

    if (video.getImageURL() != null) {
      imageCardView.setTitleText(video.getTitle());
      imageCardView.setContentText(video.getStudio());
      Activity activity = getActivity(context);
      if (activity != null) {
        imageCardView.setMainImageDimensions(ImageUtils.getDPI(240, activity), ImageUtils.getDPI(360, activity));
        cardHolder.updateCardViewImage(video.getImageURL());
      }
    }
  }

  @Nullable protected Activity getActivity(Context contextWrapper) {
    Context context = contextWrapper;
    while (context instanceof ContextWrapper) {
      if (context instanceof Activity) {
        return (Activity) context;
      }
      context = ((ContextWrapper) context).getBaseContext();
    }
    return null;
  }

  @Override public void onUnbindViewHolder(ViewHolder viewHolder) {
    CardPresenterViewHolder vh = (CardPresenterViewHolder) viewHolder;
    vh.reset();
  }

  @VisibleForTesting public class CardPresenterViewHolder extends Presenter.ViewHolder {
    private VideoContentInfo video;
    private final ImageCardView mCardView;

    public
    CardPresenterViewHolder(View view) {
      super(view);
      mCardView = (ImageCardView) view;
    }

    public void reset() {
      mCardView.setBadgeImage(null);
      mCardView.setMainImage(null);
    }

    public VideoContentInfo getMovie() {
      return video;
    }

    public void setMovie(VideoContentInfo m) {
      video = m;
    }

    public ImageCardView getCardView() {
      return mCardView;
    }

    protected void updateCardViewImage(String url) {
      Glide.with(context).load(url).into(mCardView.getMainImageView());
    }
  }
}
