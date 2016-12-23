package ua.tools.escondido.tvprogram;


import android.content.Context;
import android.test.InstrumentationTestCase;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.tools.escondido.tvprogram.data.ProgramEvent;
import ua.tools.escondido.tvprogram.mock.TVMockContext;
import ua.tools.escondido.tvprogram.utils.DateUtils;
import ua.tools.escondido.tvprogram.utils.InternalStorage;

import static org.junit.Assert.assertThat;

@RunWith(DataProviderRunner.class)
public class UtilsTest extends InstrumentationTestCase{

    private static Context context;

    @BeforeClass
    public static void setup(){
        context = new TVMockContext();
    }

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

    @Test
    public void testDataCaching() throws IOException, ClassNotFoundException {
        List<ProgramEvent> programEvents = new ArrayList<>();
        for (int i=0;i<20;i++){
            ProgramEvent event = new ProgramEvent();
            event.setName("Test Program " + i);
            event.setTime("08:"+(10+i));
            event.setProgramInfoPath("/entertainment/295608/revizor-7/");
            programEvents.add(event);
        }
        InternalStorage.writeObject(context, "KEY", programEvents);

        List<ProgramEvent> dataFromCache = (List<ProgramEvent> )InternalStorage.readObject(context, "KEY");
        assertThat(programEvents, IsEqual.equalTo(dataFromCache));
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
