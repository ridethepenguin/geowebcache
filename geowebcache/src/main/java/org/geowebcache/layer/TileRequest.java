/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Arne Kepp, The Open Planning Project, Copyright 2008
 */
package org.geowebcache.layer;

import org.geowebcache.mime.MimeType;

public class TileRequest {
    public int[] gridLoc = null;
    public MimeType mimeType = null;
    public SRS SRS = null;
    
    public TileRequest(int[] gridLoc, MimeType mimeType, SRS srs) {
        this.gridLoc = gridLoc;
        this.mimeType = mimeType;
        this.SRS = srs;
    }
    
}
