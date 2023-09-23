package io.github.jwdeveloper.spigot.tiktok.profiles.common;

import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.VariableDefinition;
import io.github.jwdeveloper.spigot.tiktok.profiles.common.definitions.MethodDefinition;
import lombok.Data;

import java.util.*;
import java.util.function.Consumer;

@Data
public class EvaluatorContext {


    public static String NULL = "NULL";

    private final Map<String, MethodDefinition> methods;

    private final Map<String, VariableDefinition> variables;

    private Consumer<String> onOutputEvent = (e)->{};

    private List<String> collectedOutput = new ArrayList<>();

    private Object returnValue;





    public EvaluatorContext() {
        this.methods = new LinkedHashMap<>();
        this.variables = new LinkedHashMap<>();
    }


    public Object getVariable(String name)
    {
        if(!variables.containsKey(name))
        {
            return NULL;
        }
        return variables.get(name).getValue();
    }

    public MethodDefinition getMethod(String name)
    {
        if(!methods.containsKey(name))
        {
            return new MethodDefinition(name,objects -> {return ""; } ,false);
        }

        return methods.get(name);
    }

    public VariableDefinition addVariable(String name, Object value) {
        return setVariable(name,value);
    }

    public MethodDefinition addMethod(MethodDefinition functionDeclaration) {
        methods.put(functionDeclaration.getName(), functionDeclaration);
        return functionDeclaration;
    }

    public List<String> getOutput()
    {
        return new ArrayList<>(collectedOutput);
    }

    public void output(String output)
    {
        collectedOutput.add(output);
        onOutputEvent.accept(output);
    }

    public void clearOutput()
    {
        collectedOutput.clear();
    }

    public VariableDefinition setVariable(String name, Object value) {
        if (!variables.containsKey(name)) {
            variables.put(name, new VariableDefinition(name, value));
        }

        var variable = variables.get(name);
        variable.setValue(value);
        return variable;
    }

    public void removeVariable(String name) {
        variables.remove(name);
    }


    public boolean hasVariable(String name)
    {
        return variables.containsKey(name);
    }

    public boolean hasMethod(String name)
    {
        return methods.containsKey(name);
    }
}

