package com.example.gametracker;
import org.junit.Assert;
import org.junit.Test;


public class PlayerDetailTest {

    @Test
    public void totalScore_firstPlace_multipliedBy3(){
        PlayerDetail p = new PlayerDetail();
        p.setFirstPlaceFinishes(1);

        Assert.assertEquals(3, p.getTotalScore());
    }

    @Test
    public void totalScore_secondPlace_multipliedBy2(){
        PlayerDetail p = new PlayerDetail();
        p.setSecondPlaceFinishes(1);

        Assert.assertEquals(2, p.getTotalScore());
    }

    @Test
    public void totalScore_thirdPlace_multipliedBy1(){
        PlayerDetail p = new PlayerDetail();
        p.setThirdPlaceFinishes(1);

        Assert.assertEquals(1, p.getTotalScore());
    }

    @Test
    public void totalScore_shouldBeCalculatedCorrectly(){
        int numFirst = 3;
        int numSecond = 3;
        int numThird = 3;

        int expected = numFirst * 3 + numSecond * 2 + numThird * 1;

        PlayerDetail p = new PlayerDetail();
        p.setFirstPlaceFinishes(numFirst); // 3 * 3 = 9
        p.setSecondPlaceFinishes(numSecond); // 3 * 2 == 6
        p.setThirdPlaceFinishes(numThird); // 3 * 1 = 1

        int actual = p.getTotalScore();
        Assert.assertEquals(expected, actual);
    }
}
