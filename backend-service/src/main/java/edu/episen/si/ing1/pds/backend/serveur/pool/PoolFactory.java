package edu.episen.si.ing1.pds.backend.serveur.pool;

public enum PoolFactory {
    Instance;

    PoolFactory() {
    }

    BlockingPool newPool(int n) {
        return new ConnectionPoolManager(n);
    }


}
