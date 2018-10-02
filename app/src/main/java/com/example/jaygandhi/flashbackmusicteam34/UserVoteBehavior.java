package com.example.jaygandhi.flashbackmusicteam34;

import android.widget.Button;

/**
 * Created by yidongluo on 2/16/18.
 *
 * A delegate class, used to help the client realize updating vote status functionality
 */
public class UserVoteBehavior implements UserVoter {
    private Track curTrack;

    public UserVoteBehavior(Track curTrack){
        this.curTrack = curTrack;
    }

    /*
     * Update the vote button based on the current vote status
     */
    public void updateVoteButton(Button like_dislike){
        int trackStatus = curTrack.getUserVote();
        switch (trackStatus) {
            case TrackStatus.LIKE:
                like_dislike.setText(R.string.status_like);
                break;
            case TrackStatus.DISLIKE:
                like_dislike.setText(R.string.status_dislike);
                break;
            default:
                like_dislike.setText(R.string.status_neutral);
        }
    }

    public Track getCurTrack() {
        return curTrack;
    }

}
