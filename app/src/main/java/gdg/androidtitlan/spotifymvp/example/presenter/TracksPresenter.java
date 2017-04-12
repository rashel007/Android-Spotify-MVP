/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gdg.androidtitlan.spotifymvp.example.presenter;

import gdg.androidtitlan.spotifymvp.example.data.model.Track;
import gdg.androidtitlan.spotifymvp.example.interactor.TracksInteractor;
import io.reactivex.functions.Consumer;
import java.util.List;

public class TracksPresenter implements Presenter<TracksMvpView> {

  private TracksMvpView tracksMvpView;
  private TracksInteractor tracksInteractor;

  public TracksPresenter(TracksInteractor tracksInteractor) {
    this.tracksInteractor = tracksInteractor;
  }

  @Override public void setView(TracksMvpView view) {

    if (view == null) {
      throw new IllegalArgumentException("You can't set a null view");
    }

    tracksMvpView = view;
  }

  @Override public void detachView() {
    tracksMvpView = null;
  }

  public void onSearchTracks(String string) {
    tracksMvpView.showLoading();
    tracksInteractor.loadData(string).subscribe(new Consumer<List<Track>>() {
      @Override public void accept(List<Track> tracks) throws Exception {
        tracksMvpView.hideLoading();
        tracksMvpView.renderTracks(tracks);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        throwable.printStackTrace();
      }
    });
  }

  public void launchArtistDetail(List<Track> tracks, Track track, int position) {
    tracksMvpView.launchTrackDetail(tracks, track, position);
  }
}
