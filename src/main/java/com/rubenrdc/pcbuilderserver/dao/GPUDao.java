package com.rubenrdc.pcbuilderserver.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.rubenrdc.pcbuilderserver.models.GPU;
import static com.rubenrdc.pcbuilderserver.models.interfaces.Utilities.generateImageIcon;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.List;
import javax.swing.ImageIcon;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Ruben
 */
public class GPUDao {
    private static final DaoConnection dao = new DaoConnection();

    public static List<GPU> getListGPU(String Type) {
        if (dao.EstablecerC()) {
            List<GPU> list = new ArrayList<>();
            
            Bson filter = Filters.eq("type", Type);
            
            FindIterable<Document> genericQuery = dao.genericQuery("GPU", filter);
            
            MongoCursor<Document> iterator = genericQuery.iterator();

            while (iterator.hasNext()) {
                Document doc = iterator.next();
                ImageIcon imagen = generateImageIcon(doc.getString("imagen"));
                list.add(new GPU(doc.getObjectId("_id"), imagen, doc.getString("title"), doc.getString("marca"),
                        doc.getString("typeMemory"), doc.getInteger("memoryVRam")));
            }
            sort(list);
            dao.closeCo();
            return list;
        }
        return null;
    }

    public static GPU getMoreInfo(ObjectId id) {
        if (dao.EstablecerC()) {
            GPU g = null;

            Bson filter = Filters.eq("_id", id);
            FindIterable<Document> genericQuery = dao.genericQuery("Cooler", filter);
            Document doc = genericQuery.first();

            if (doc != null) {
                ImageIcon imagen = generateImageIcon(doc.getString("imagen"));

                g = new GPU(doc.getObjectId("_id"),
                        imagen, doc.getString("title"),
                        doc.getString("marca"),
                        doc.getString("type"),
                        doc.getString("chipsetGpu"),
                        doc.getString("typeMemory"),
                        doc.getInteger("VGA"),
                        doc.getInteger("DVI"),
                        doc.getInteger("HDMI"),
                        doc.getInteger("displayPorts"),
                        doc.getInteger("height"),
                        doc.getInteger("length"),
                        doc.getInteger("energyConsumption"),
                        doc.getInteger("memoryVRam"),
                        doc.getInteger("speedMemory"),
                        doc.getInteger("recommMinimWatts"),
                        doc.getString("oficialDocumentation"));
            }
            dao.closeCo();
            return g;
        }
        return null;
    }
}
