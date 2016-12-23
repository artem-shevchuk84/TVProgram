package ua.tools.escondido.tvprogram.mock;

import android.test.mock.MockContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class TVMockContext extends MockContext {
    @Override
    public String[] fileList() {
        return new String[0];
    }

    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return new FileInputStream("//eeee.dat");
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return new FileOutputStream("//eeee.dat");
    }
}
