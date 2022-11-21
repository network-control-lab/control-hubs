# Finding control hubs in complex networks

#### A node may occupy distinct positions in control paths of different control schemes, i.e., it may be a driver, a tail, or a middle node in different control schemes. Some nodes may always remain as middle nodes in all control schemes, and such nodes are defined as control hubs.

### This method was cited in:
#### 1. **_"Total controllability analysis discovers explainable drugs for Covid-19 therapy and prevention." Wei et al._**
#### 2. **_"Identification of cancer keeping genes as therapeutic targets by finding network control hubs." Zhang et al._**

### Project explanation:
#### 1. "/src/main"
The path "/src/main" is the main function of the code, which provides "node_classification","sensitive_control_hub" and "control_scheme" function.
#### 2. "/src/control_package"
The path "/src/control_package" contains the functions required by the main function to ensure that the main function can be executed successfully.
#### 3. "/net" (sample data)
The path "/net" contains the network mentioned in the paper, and a sample network "sample_network.net".
#### 4. "/result"
The path "/result" records the results obtained by executing the main function.
#### 5. "/Supplementary materials"
Raw data for research using the control hub method.
#### If you want to use the functions we provide, please put a network file in a specific format into the "/net" folder and execute the "/src/main/demo.java" file, you can select the functions you want to execute and annotate the functions you don't want to execute.
