package com.google.gson.internal.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.l;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.a;
import com.google.gson.stream.b;
import java.util.BitSet;

class G extends l {
    G() {
    }

    public BitSet cR(b bVar) {
        if (bVar.df() != JsonToken.NULL) {
            BitSet bitSet = new BitSet();
            bVar.db();
            JsonToken df = bVar.df();
            int i = 0;
            while (df != JsonToken.END_ARRAY) {
                boolean z;
                switch (M.dy[df.ordinal()]) {
                    case 1:
                        if (bVar.dp() != 0) {
                            z = true;
                            break;
                        }
                        z = false;
                        break;
                    case 2:
                        z = bVar.dl();
                        break;
                    case 3:
                        String dk = bVar.dk();
                        try {
                            if (Integer.parseInt(dk) != 0) {
                                z = true;
                                break;
                            }
                            z = false;
                            break;
                        } catch (NumberFormatException e) {
                            throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + dk);
                        }
                    default:
                        throw new JsonSyntaxException("Invalid bitset value type: " + df);
                }
                if (z) {
                    bitSet.set(i);
                }
                i++;
                df = bVar.df();
            }
            bVar.dc();
            return bitSet;
        }
        bVar.dm();
        return null;
    }

    public /* bridge */ /* synthetic */ void cT(a aVar, Object obj) {
        eb(aVar, (BitSet) obj);
    }

    public void eb(a aVar, BitSet bitSet) {
        if (bitSet != null) {
            aVar.dx();
            for (int i = 0; i < bitSet.length(); i++) {
                aVar.dF((long) (!bitSet.get(i) ? 0 : 1));
            }
            aVar.dy();
            return;
        }
        aVar.dD();
    }
}
