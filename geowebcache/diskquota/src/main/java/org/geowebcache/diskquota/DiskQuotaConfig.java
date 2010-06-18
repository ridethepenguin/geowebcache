package org.geowebcache.diskquota;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DiskQuotaConfig {

    private static final int DEFAULT_DISK_BLOCK_SIZE = 4096;

    private static final int DEFAULT_CLEANUP_FREQUENCY = 10;

    private static final TimeUnit DEFAULT_CLEANUP_UNITS = TimeUnit.MINUTES;

    private int diskBlockSize;

    private int cacheCleanUpFrequency;

    private TimeUnit cacheCleanUpUnits;

    private int maxConcurrentCleanUps;

    private List<LayerQuota> layerQuotas;

    private transient Map<String, LayerQuota> layerQuotasMap;

    public int getDiskBlockSize() {
        if (diskBlockSize == 0) {
            diskBlockSize = DEFAULT_DISK_BLOCK_SIZE;
        }
        return diskBlockSize;
    }

    public void setDiskBlockSize(int blockSizeBytes) {
        if (blockSizeBytes <= 0) {
            throw new IllegalArgumentException("Block size shall be a positive integer");
        }
        this.diskBlockSize = blockSizeBytes;
    }

    public int getCacheCleanUpFrequency() {
        return cacheCleanUpFrequency == 0 ? DEFAULT_CLEANUP_FREQUENCY : cacheCleanUpFrequency;
    }

    public void setCacheCleanUpFrequency(int cacheCleanUpFrequency) {
        if (cacheCleanUpFrequency < 0) {
            throw new IllegalAccessError("cacheCleanUpFrequency shall be a positive integer");
        }
        this.cacheCleanUpFrequency = cacheCleanUpFrequency;
    }

    public TimeUnit getCacheCleanUpUnits() {
        return cacheCleanUpUnits == null ? DEFAULT_CLEANUP_UNITS : cacheCleanUpUnits;
    }

    public void setCacheCleanUpUnits(TimeUnit cacheCleanUpUnit) {
        if (cacheCleanUpUnit == null) {
            throw new IllegalArgumentException("cacheCleanUpUnits can't be null");
        }
        this.cacheCleanUpUnits = cacheCleanUpUnit;
    }

    @SuppressWarnings("unchecked")
    public List<LayerQuota> getLayerQuotas() {
        return layerQuotas == null ? Collections.EMPTY_LIST : layerQuotas;
    }

    public void setLayerQuotas(List<LayerQuota> quotas) {
        this.layerQuotas = quotas;
        this.layerQuotasMap = null;
    }

    public void setLayerQuota(String layerName, LayerQuota lq) {
        getLayerQuotasMap().put(layerName, lq);
    }

    public LayerQuota getLayerQuota(final String layerName) {
        LayerQuota quota = getLayerQuotasMap().get(layerName);
        return quota;
    }

    private Map<String, LayerQuota> getLayerQuotasMap() {
        if (layerQuotasMap == null) {
            layerQuotasMap = new HashMap<String, LayerQuota>();

            if (layerQuotas != null) {
                for (LayerQuota lq : layerQuotas) {
                    layerQuotasMap.put(lq.getLayer(), lq);
                }
            }
        }
        return layerQuotasMap;
    }

    public void remove(LayerQuota lq) {
        for (Iterator<LayerQuota> it = layerQuotas.iterator(); it.hasNext();) {
            LayerQuota quota = it.next();
            if (quota.getLayer().equals(lq.getLayer())) {
                it.remove();
                getLayerQuotasMap().remove(lq.getLayer());
                break;
            }
        }
    }

    /**
     * @return number of explicitly configured layers (ie, not affected by
     *         {@link #getDefaultQuota() default quota})
     */
    public int getNumLayers() {
        return layerQuotas == null ? 0 : layerQuotas.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("[");
        for (LayerQuota lq : getLayerQuotas()) {
            sb.append("\n\t").append(lq);
        }
        sb.append("]");
        return sb.toString();
    }

    public int getMaxConcurrentCleanUps() {
        return maxConcurrentCleanUps == 0 ? 2 : maxConcurrentCleanUps;
    }

    public void setMaxConcurrentCleanUps(int nThreads) {
        this.maxConcurrentCleanUps = nThreads;
    }

}