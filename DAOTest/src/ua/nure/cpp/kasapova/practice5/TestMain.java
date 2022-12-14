package ua.nure.cpp.kasapova.practice5;

import ua.nure.cpp.kasapova.practice5.dao.interfaces.*;
import ua.nure.cpp.kasapova.practice5.dao.DAOFactory;
import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.entity.*;
import java.util.List;


public class TestMain {
    public static void main(String[] args) {

        ClientDAO clDAO = DAOFactory.getClientDAO();
        CutterDAO cDAO = DAOFactory.getCutterDAO();
        ModelDAO mDAO = DAOFactory.getModelDAO();
        FabricDAO fDAO = DAOFactory.getFabricDAO();
        ReceiptDAO rDAO = DAOFactory.getReceiptDAO();

        List<Client> clients = null;
        List<Cutter> cutters = null;
        List<Model> models = null;
        List<Fabric> fabrics = null;
        List<Receipt> receipts = null;

        Client newClient = new Client();
        newClient.setName("Clava");
        newClient.setSurname("Krypsy");


        try {
            System.out.println("---------------Test add() in client-----------------");
            System.out.println("---Load all clients");
            System.out.println(clDAO.loadAll());
            clDAO.add(newClient);
            System.out.println("---Load all clients after adding one");
            System.out.println(clDAO.loadAll());


            System.out.println("---------------Test update() in cutter-----------------");
            System.out.println("---Load cutter to update");
            System.out.println(cDAO.findById(1));
            cDAO.updateSurname(1, "Korolyov");
            System.out.println("---Load cutter after updating");
            System.out.println(cDAO.findById(1));



            System.out.println("---------------Test findById() in model-----------------");
            System.out.println("---Load all models");
            System.out.println(mDAO.loadAll());
            System.out.println("---Find model with id=1");
            System.out.println(mDAO.findById(1));

            System.out.println("---------------Test delete() in receipt-----------------");
            System.out.println("---Load all receipts");
            System.out.println(rDAO.loadAll());
            rDAO.deleteById(5);
            System.out.println("---Load receipt after updating");
            System.out.println(rDAO.loadAll());

            System.out.println("---------------Test delete() in fabric-----------------");
            System.out.println("---Load all fabrics");
            System.out.println(fDAO.loadAll());
            fDAO.deleteById(5);
            System.out.println("---Load all fabrics after deleting one");
            System.out.println(fDAO.loadAll());


        } catch (DBException e) {
            System.err.println("Exception " + e);
        }


    }
}
