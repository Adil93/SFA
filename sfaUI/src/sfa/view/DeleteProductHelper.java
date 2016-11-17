package sfa.view;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class DeleteProductHelper {
    public DeleteProductHelper() {
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public String b3_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
         operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "delete";
       
    }
    public void onDialogAction(DialogEvent dialogEvent) {
        // Add event code here...
        DialogEvent.Outcome outcome = dialogEvent.getOutcome();
        if(outcome == DialogEvent.Outcome.ok){
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
           
            Object result=operationBinding.execute();
            
                operationBinding=bindings.getOperationBinding("Commit");
                operationBinding.execute();
            }
    }
}
