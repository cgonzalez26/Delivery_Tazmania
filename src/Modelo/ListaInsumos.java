package Modelo;

import Modelo.Insumos;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Colo-PC
 */
public class ListaInsumos extends AbstractListModel {
    
    private ArrayList<Insumos> lista = new ArrayList<>();
    
    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public Object getElementAt(int i) {
        Insumos insumo=lista.get(i);
        return insumo.getDescripcion();
    }
    
    public void AgregarInsumo(Insumos i){
        lista.add(i);
        this.fireIntervalAdded(this, getSize(), getSize());
    }
    
    public void EliminarInsumo(int i){
        lista.remove(i);
        this.fireIntervalRemoved(i,getSize(),getSize());
    }
            
    public Insumos getInsumo(int i){
        return lista.get(i);
    }
}
