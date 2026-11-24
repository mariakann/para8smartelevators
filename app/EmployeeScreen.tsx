import React, { useState } from "react";
import {
    View,
    Text,
    TextInput,
    TouchableOpacity,
    StyleSheet,
    ScrollView,
    Alert,
} from "react-native";

const minFloor = 0;
const maxFloor = 9;
const totalElevators = 5;

export default function EmployeeScreen() {
    
    /*
    AI-Tool: ChatGpt
     AI-Model: gpt-4o
     AI-Task: State definition and array mapping for floors in EmployeeScreen
     AI-Prompt-1: Πώς να ορίσω τα states και τα array.from για τους ορόφους
     AI-Tokens: ~400
     AI-Role: Developer Assistant
    */
    
    const [floors] = useState(
        Array.from({ length: maxFloor - minFloor + 1 }, (_, index) => ({
            id: index,
            name: index === 0 ? "ΙΣΟΓΕΙΟ (Ground)" : `ΟΡΟΦΟΣ ${index}`,
            isRestricted: index >= 7 && index <= 9, // Όροφοι 7, 8, 9 είναι περιορισμένοι
        }))
    );
    
    const [elevators] = useState(
        Array.from({ length: totalElevators }, (_, index) => ({
            id: index + 1,
            capacity: 8,
            currentFloor: 0,
            status: "idle",
        }))
    );
    
    const [employeeId, setEmployeeId] = useState("");
    const [originFloor, setOriginFloor] = useState(null);
    const [destinationFloor, setDestinationFloor] = useState(null);
    const [priority, setPriority] = useState("3");
    const [meetingCode, setMeetingCode] = useState("");

    const isRestrictedFloor = (floor: number) => {
        return floor === minFloor || floor === maxFloor;
    };
    
    const handleSubmitting = () => {

        const employee = employeeId.trim();
        const meeting = meetingCode.trim();
        
        if (originFloor === null || destinationFloor === null || priority === "") {
            Alert.alert("Παρακαλώ συμπληρώστε τους ορόφους και την προτεραιότητα!");
            return;
        }
        
        
        if (originFloor === destinationFloor) {
            Alert.alert("Η διαδρομή ΔΕΝ είναι έγκυρη.", `\n\n Ο όροφος αφετηρίας πρέπει να διαφέρει από τον όροφο προορισμού.`);
            return;
        }

        if (!["1", "2", "3"].includes(priority.trim())) {
            Alert.alert("Η προτεραιότητα ΔΕΝ είναι έγκυρη.", "\n\n Επιλέξτε 1 για Emergency, 2 για Meeting ή 3 για Normal.");
            return;
        }
        const priorityLevel = Number(priority.trim());
        const restrictedFloor = isRestrictedFloor(originFloor) || isRestrictedFloor(destinationFloor);
        const employeeRestricted = priorityLevel !== 2 || restrictedFloor;

        if (employeeRestricted && employee === "") {
            Alert.alert("Υποχρεωτικό ID Εργαζομένου", restrictedFloor ? "Η διαδρομή περιλαμβάνει περιορισμένο όροφο. Παρακαλώ εισάγετε το ID Εργαζομένου." : "Για αίτημα Emergency και Normal απαιτείται ID Εργαζομένου.");
            return;
        }

        if (priorityLevel === 2 && meeting === "") {
            Alert.alert("Υποχρεωτικός Κωδικός Meeting", "Για αίτημα Meeting συμπληρώστε τον Κωδικό Meeting.");
            return;
        }

        let requestType = "";
        switch (priorityLevel) {
            case 1:
                requestType = "Emergency";
                break;
            case 2:
                requestType = "Meeting";
                break;
            case 3:
                requestType = "Normal";
                break;
        }

        const employeeText = employee !== "" ? employee : "ΔΕΝ ΒΡΕΘΗΚΕ";

        let resultMessage = `Tύπος Αιτήματος: ${requestType}\nID Εργαζομένου: ${employeeText}\nΑπό Όροφο: ${originFloor} -> Διαδρομή: ${destinationFloor}\nΠροτεραιότητα: ${priorityLevel}`;

        if (priorityLevel === 2) {
            resultMessage += `\nΚωδικός Meeting: ${meeting}`;
        }

        if (restrictedFloor) {
            resultMessage += `\nΠΡΟΣΟΧΗ: Η διαδρομή περιλαμβάνει όροφο περιορισμένης πρόσβασης!`;
        }
        
        Alert.alert(
            "Το αίτημα καταχωρήθηκε ΕΠΙΤΥΧΩΣ!!",
            resultMessage
        );
        
        setEmployeeId("");
        setOriginFloor(null);
        setDestinationFloor(null);
        setPriority("3");
        setMeetingCode("");
    };
    
    return (
    
     /*
     AI-Tool: ChatGpt / VS Code Corilot
     AI-Model: gpt-4o
     AI-Task: Creation and formatting of ScrollingView with floor
     AI-Prompt-1: Δημιούργησε ένα ScrollView σε React Native για την εμφάνιση ορόφων
     AI-Tokens: ~500
     AI-Role: Developer Assistant
     */

    <ScrollView contentContainerStyle={styles.container}>
        <Text style={styles.title}>ELEVATE</Text>
        <Text style={styles.subtitle}>Employee Screen (0-9 Όροφοι / 5 Ανελκυστήρες)</Text>
        <Text style={styles.heading}>Διαθέσιμοι Όροφοι</Text>
        {floors.map((floor) => (
            <View key={floor.id} style={styles.card}>
                <Text style={styles.cardTitle}>{floor.name} (ID: {floor.id})</Text>
                <Text style={styles.text}>
                Πρόσβαση:{" "}
                    <Text style={floor.isRestricted ? styles.restricted : styles.normal}>
                        {floor.isRestricted ? "Περιορισμένη" : "Κανονική"}
                        </Text>
                        </Text>
                        </View>
                    ))}
                    
                    <Text style={styles.heading}>Ανελκυστήρες</Text>
                    {elevators.map((elevator) => (
                        <View key={elevator.id} style={styles.card}>
                            <Text style={styles.cardTitle}>Ανελκυστήρας {elevator.id}</Text>
                            <Text style={styles.text}>Χωρητικότητα: {elevator.capacity} άτομα</Text>
                            <Text style={styles.text}>Τρέχων Όροφος: {elevator.currentFloor}</Text>
                            <Text style={styles.text}>
                                Κατάσταση:{" "}
                                <Text style={styles.statusText}>{elevator.status}</Text>
                                </Text>
                            </View>
                        ))}
                        
                        <Text style={styles.heading}>Νέο Αίτημα Μετακίνησης</Text>

                        <Text style={styles.label}>Επίπεδο Προτεραιότητας</Text>
                        <Text style={styles.text}>1 = Emergency | 2 = Meeting | 3 = Normal</Text>
                        <TextInput
                        style={styles.input}
                        value={priority}
                        onChangeText={setPriority}
                        keyboardType="numeric"
                        placeholder="1, 2 ή 3"
                        placeholderTextColor="#666"
                        />

                        {priority.trim() === "2" && (
                            <>
                            <Text style={styles.label}>Κωδικός Meeting (Υποχρεωτικό)
                            </Text>
                            <TextInput
                            style={styles.input}
                            value={meetingCode}
                            onChangeText={setMeetingCode}
                            placeholder="πχ CODE-123"
                            placeholderTextColor="#666"
                            
                            />
                            </>
                        )}

                        <Text style={styles.label}>ID Εργαζομένου</Text>
                        <TextInput 
                        style={styles.input}
                        value={employeeId}
                        onChangeText={setEmployeeId}
                        keyboardType="numeric"
                        placeholder="πχ 101"
                        placeholderTextColor="#666"
                        />
                        
                        <Text style={styles.label}>
                        Υποχρεωτικό για Emergency και Normal διαδρομές, καθώς και για διαδρομές που περιλαμβάνουν όροφο περιορισμένης πρόσβασης. Προαιρετικό για άλλους τύπους αιτημάτων.
                        </Text>

                        {/*
                        AI-Tool: ChatGpt
                        AI-Model: GPT-4o
                        AI-Task: Implement origin floor button grid in EmployeeScreen
                        AI-Prompt-1: "Δημιούργησε ένα grid με κουμπιά για την επιλογή ορόφων αφετηρίας (0-9) σε React Native."
                        AI-Tokens: ~410
                        AI-Role: Developer
                        */}

                        {/* ΚΟΥΜΠΙΑ ΟΡΟΦΩΝ ΑΦΕΤΗΡΙΑΣ (0-9)*/}
                        <Text style={styles.label}>Όροφος Αφετηρίας (0-9)</Text>
                        <View style={styles.floorGrid}>
                            {Array.from({ length: maxFloor - minFloor + 1 }, (_, i) => i).map((floor) => (
                                <TouchableOpacity
                                    key={`origin-${floor}`}
                                    style={[
                                        styles.floorButton,
                                        originFloor === floor && styles.selectedFloorButton,
                                    ]}
                                    onPress={() => setOriginFloor(floor)}
                                    >
                                    <Text
                                        style={[
                                            styles.floorButtonText,
                                            originFloor === floor && styles.selectedFloorButtonText,
                                        ]}
                                        >
                                        {floor}
                                    </Text>
                                </TouchableOpacity>
                            ))}
                        </View>
                        
                        {/* ΚΟΥΜΠΙΑ ΟΡΟΦΩΝ ΠΡΟΟΡΙΣΜΟΥ (0-9)*/}
                        <Text style={styles.label}>Όροφος Προορισμού (0-9)</Text>
                        <View style={styles.floorGrid}>
                            {Array.from({ length: maxFloor - minFloor + 1 }, (_, i) => i).map((floor) => (
                                <TouchableOpacity
                                    key={`destination-${floor}`}
                                    style={[
                                        styles.floorButton,
                                        destinationFloor === floor && styles.selectedFloorButton,
                                    ]}
                                    onPress={() => setDestinationFloor(floor)}
                                    >
                                    <Text
                                        style={[
                                            styles.floorButtonText,
                                            destinationFloor === floor && styles.selectedFloorButtonText,
                                        ]}
                                        >
                                        {floor}
                                    </Text>
                                </TouchableOpacity>
                            ))}
                        </View>

                        <TouchableOpacity style={styles.button} onPress={handleSubmitting}>
                            <Text style={styles.buttonText}>Υποβολή Αιτήματος</Text>
                        </TouchableOpacity>
                        </ScrollView>
    );
}

                        const styles = StyleSheet.create({
                            container: {
                                padding: 20,
                                paddingTop: 50,
                                backgroundColor: "#121212",
                                flexGrow: 1,
                            },
                            title: {
                                fontSize: 38,
                                fontWeight: "bold",
                                textAlign: "center",
                                color: "#1f82ac",
                                marginTop: 1,
                            },
                            subtitle: {
                                fontSize: 14,
                                textAlign: "center",
                                marginBottom: 25,
                                color: "#94a3b8",
                            },
                            heading: {
                                fontSize: 18,
                                fontWeight: "bold",
                                marginTop: 25,
                                marginBottom: 12,
                                color: "#1f82ac",
                            },
                            card: {
                                backgroundColor: "#1e1e1e",
                                padding: 16,
                                borderRadius: 10,
                                marginBottom: 12,
                                borderWidth: 1,
                                borderColor: "#334155",  
                            },
                            cardTitle: {
                                fontSize: 16,
                                fontWeight: "bold",
                                marginBottom: 6,
                                color: "#ffffff",
                            },
                            text: { 
                                fontSize: 15,
                                color: "#cbd5e0",
                                marginTop: 0,
                                lineHeight: 24,
                            },
                            restricted: {
                                color: "#cb6a6a",
                                fontWeight: "bold",
                            },
                            normal: {
                                color: "#8e9eb0",
                                fontWeight: "bold",
                            },
                            statusText: {
                                color: "#38bdf8",
                                fontStyle: "italic",
                            },
                            label: {
                                fontSize: 16,
                                fontWeight: "bold",
                                marginTop: 14,
                                marginBottom: 6,
                                color: "#94a3b8",
                            },
                            helperText: {
                                fontSize: 16,
                                color: "#666",
                                marginBottom: 10,
                            },
                            input: {
                                backgroundColor: "#1e1e1e",
                                borderColor: "#334155",
                                borderWidth: 1,
                                padding: 10,
                                marginBottom: 20,
                                borderRadius: 5,
                                fontSize: 16,
                                color: "#666",
                            },
                            floorGrid: {
                                flexDirection: "row",
                                flexWrap: "wrap",
                                gap: 10,
                                marginBottom: 10,
                            },
                            floorButton: {
                                width: "18%",
                                paddingVertical: 10,
                                backgroundColor: "#e0e0e0",
                                borderRadius: 5,
                                borderWidth: 1,
                                borderColor: "#334155",
                                alignItems: "center",
                                marginBottom: 10,
                            },
                            selectedFloorButton: {
                                backgroundColor: "#2387b9",
                                borderColor: "#1a5d7a",
                            },
                            button: {
                                backgroundColor: "#1f82ac",
                                padding: 16,
                                borderRadius: 8,
                                alignItems: "center",
                                marginTop: 30,
                                marginBottom: 40,
                                shadowColor: "#1f82ac",
                                shadowOpacity: 0.4,
                                shadowRadius: 8,
                                elevation: 4,
                            },
                            buttonText: {
                                color: "#e6dcdc",
                                fontSize: 16,
                                fontWeight: "bold",
                            },
                        });

                       
                        