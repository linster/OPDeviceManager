package okio;

final class e {
    static p uA;
    static long uB;

    private e() {
    }

    static p Ap() {
        synchronized (e.class) {
            if (uA == null) {
                return new p();
            }
            p pVar = uA;
            uA = pVar.uR;
            pVar.uR = null;
            uB -= 2048;
            return pVar;
        }
    }

    static void Aq(p pVar) {
        Object obj = null;
        if (pVar.uR != null || pVar.uS != null) {
            throw new IllegalArgumentException();
        } else if (!pVar.uP) {
            synchronized (e.class) {
                if (uB + 2048 <= 65536) {
                    obj = 1;
                }
                if (obj == null) {
                    return;
                }
                uB += 2048;
                pVar.uR = uA;
                pVar.limit = 0;
                pVar.pos = 0;
                uA = pVar;
            }
        }
    }
}
