package net.Lucent.ArrayFormations.programmableArrays.serializers;

import net.Lucent.ArrayFormations.programmableArrays.AbstractFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.constantNodes.ConstantInputNode;
import net.Lucent.ArrayFormations.programmableArrays.coreNodes.AbstractCoreFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.dataChannels.AbstractDataChannel;

import java.rmi.MarshalledObject;
import java.util.*;

//used to serialize nodes too and from json strings so it can easily be stored as data components or nbt data
public class ProgrammableNodesSerializer {



private static String getNodeNamespace(AbstractFormationNode node, Map<AbstractFormationNode,String> namespaceMap,
                                       Map<String,Integer> namespaceGenMap, Map<String,String> namespaceToNode){
        String namespace = "";
        if(namespaceMap.containsKey(node)) namespace = namespaceMap.get(node);
        else{
            if(namespaceGenMap.containsKey(node.getNodeAsString())){
                Integer id = namespaceGenMap.get(node.getNodeAsString()) +1;
                namespaceGenMap.put(node.getNodeAsString(),id);
                namespace = node.getNodeAsString()+id.toString();
                namespaceMap.put(node,namespace);
            }else{
                namespaceGenMap.put(node.getNodeAsString(),0);
                namespaceMap.put(node,node.getNodeAsString());
                namespace = node.getNodeAsString();
            }
        }
        return namespace;
    }

    private static void serializeNode(AbstractFormationNode node, Map<AbstractFormationNode,String> namespaceMap,
                                                Map<String,Integer> namespaceGenMap, Map<String,String> namespaceToNode){
        StringBuilder finalString = new StringBuilder();

        //get the node namespace
        String namespace = getNodeNamespace(node,namespaceMap,namespaceGenMap,namespaceToNode);
        finalString = new StringBuilder(namespace + ": {");
        finalString.append("\nnodeType: ").append(node.getNodeAsString()).append(",");
        List<String> connections = node.getConnections();
        finalString.append("\nconnections : { \n");
        for(String connection:connections){
            AbstractDataChannel<?> dataChannel = node.getDataChannel(connection);
            if(dataChannel == null){
                finalString.append(connection).append(": null,\n");

                continue;
            }
            serializeNode(dataChannel.getInputNode(),namespaceMap,namespaceGenMap,namespaceToNode);
            finalString.append(connection).append(": { \n")
                    .append("namespace: ").append(namespaceMap.get(dataChannel.getInputNode())).append(",\n")
                    .append("connection: ").append(dataChannel.connection).append("},\n");

        }
        if(!connections.isEmpty()) finalString.deleteCharAt(finalString.length()-1);
        finalString.append("}");

        //for nodes with unique fields
        if(node.getNodeAsString().equals("ConstantInputNode")){
            finalString.append(",\ndata:{");
            Optional<?> data = ((ConstantInputNode<?>) node).getData();
            if(data.isPresent()) {
                finalString.append("\nvalue: ").append(data.get().toString()).append(",");
                finalString.append("\ntype: ").append(data.get().getClass().toString()).append("}");
            } else finalString.append(",\n data: {}");
        }

        namespaceToNode.put(namespace, String.valueOf(finalString));

    }


    public static String serialize(List<AbstractCoreFormationNode> roots){

        Map<AbstractFormationNode,String> namespaceMap = new HashMap<>();
        Map<String,Integer> namespaceGenMap = new HashMap<>();
        Map<String,String> namespaceToNode = new HashMap<>();
        List<String> nodeStringList = new ArrayList<>();
        for(AbstractCoreFormationNode coreNode : roots){
            serializeNode(coreNode,namespaceMap,namespaceGenMap,namespaceToNode);
        }
        for(String namespace : namespaceToNode.values()){
            System.out.println(namespace);
            System.out.println("=================");
        }

        return "";
    }




}
