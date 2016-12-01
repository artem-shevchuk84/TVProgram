package ua.tools.escondido.tvprogram;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ua.tools.escondido.tvprogram.utils.DateUtils;

import static org.junit.Assert.assertThat;

@RunWith(DataProviderRunner.class)
public class UtilsTest {

    @Test
    @UseDataProvider("monthDataProvider")
    public void testDateFormat(Date testDate){
        String date = DateUtils.formatDate(DateUtils.DISPLAY_DATE_FORMAT, testDate);
        Boolean isConteinsMonth = date.contains("січня") ||
                date.contains("лютого") ||
                date.contains("березня") ||
                date.contains("квітня") ||
                date.contains("травня") ||
                date.contains("червня") ||
                date.contains("липня") ||
                date.contains("серпня") ||
                date.contains("вересня") ||
                date.contains("жовтня") ||
                date.contains("листопада") ||
                date.contains("грудня");
        assertThat(isConteinsMonth, Is.is(true));
    }

    @DataProvider
    public static Object[][] monthDataProvider() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Date jan = new Date();
        Date feb = new Date();
        Date mar = new Date();
        Date apr = new Date();
        Date may = new Date();
        Date jun = new Date();
        Date jul = new Date();
        Date aug = new Date();
        Date sep = new Date();
        Date oct = new Date();
        Date nov = new Date();
        Date dec = new Date();
        try {
            jan = dateFormat.parse("1 Jan 2016");
            feb = dateFormat.parse("1 Feb 2016");
            mar = dateFormat.parse("1 Mar 2016");
            apr = dateFormat.parse("1 Apr 2016");
            may = dateFormat.parse("1 May 2016");
            jun = dateFormat.parse("1 Jun 2016");
            jul = dateFormat.parse("1 Jul 2016");
            aug = dateFormat.parse("1 Aug 2016");
            sep = dateFormat.parse("1 Sep 2016");
            oct = dateFormat.parse("1 Oct 2016");
            nov = dateFormat.parse("1 Nov 2016");
            dec = dateFormat.parse("1 Dec 2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Object[][] {
                { jan },
                { feb },
                { mar },
                { apr },
                { may },
                { jun },
                { jul },
                { aug },
                { sep },
                { oct },
                { nov },
                { dec }
        };
    }
}
