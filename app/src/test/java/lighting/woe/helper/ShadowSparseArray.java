package lighting.woe.helper;

import android.util.SparseArray;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.HashMap;

@Implements(SparseArray.class)
public class ShadowSparseArray<T>{
    HashMap<Integer, T> mItems = new HashMap<>();

    @SuppressWarnings("unused")
    @Implementation
    T get(int index){
        return mItems.get(index);
    }
}
