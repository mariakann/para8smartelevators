import React from "react";
import { ScrollView, StyleSheet, Text, View } from "react-native";

// Admin screen: the floor map and a card for every elevator, with fixed data.
// TODO: read the elevators from Eirini's list once it exists

type ElevatorStatus = "IDLE" | "UP" | "DOWN";

// same fields as the elevators table, nextStops as in the status update of the api
interface Elevator {
    id: string;
    capacity: number;
    currentFloor: number;
    status: ElevatorStatus;
    nextStops: number[];
}

// same floors as the floors table, 0 is the ground floor
const minFloor = 0;
const maxFloor = 9;

// from this floor and up is_restricted is true in the floors table
const firstRestrictedFloor = 7;

// the map shows the top floor first
const floors = Array.from({ length: maxFloor - minFloor + 1 }, (_, index) => ({
    id: index,
    name: index === 0 ? "Ισόγειο" : `Όροφος ${index}`,
    isRestricted: index >= firstRestrictedFloor,
})).reverse();

// example data until Eirini's list exists, A, B and C like the elevators table
const elevators: Elevator[] = [
    { id: "A", capacity: 8, currentFloor: 0, status: "IDLE", nextStops: [] },
    { id: "B", capacity: 8, currentFloor: 3, status: "UP", nextStops: [5, 8] },
    { id: "C", capacity: 8, currentFloor: 7, status: "DOWN", nextStops: [2, 0] },
];

const rowHeight = 22;

export default function AdminDashboard({ role }: { role: string }) {
    if (role !== "ADMIN") {
        return (
            <View style={styles.container}>
                <Text style={styles.title}>ELEVATE</Text>
                <Text style={styles.subtitle}>Η οθόνη είναι μόνο για τον διαχειριστή.</Text>
            </View>
        );
    }

    return (
        <ScrollView contentContainerStyle={styles.container}>
            <Text style={styles.title}>ELEVATE</Text>
            <Text style={styles.subtitle}>
                Admin Screen ({minFloor}-{maxFloor} Όροφοι / {elevators.length} Ανελκυστήρες)
            </Text>
            <Text style={styles.heading}>Χάρτης Ορόφων (δεδομένα παραδείγματος)</Text>

            <View style={styles.card}>
                <View style={styles.row}>
                    <Text style={[styles.floorColumn, styles.headerText]}>Όροφος</Text>
                    {elevators.map((elevator) => (
                        <Text key={elevator.id} style={[styles.cell, styles.headerText]}>
                            {elevator.id}
                        </Text>
                    ))}
                </View>

                {floors.map((floor) => (
                    <View key={floor.id} style={[styles.row, floor.id % 2 === 0 && styles.evenRow]}>
                        <View style={styles.floorColumn}>
                            <Text style={styles.floorNumber}>{floor.id}</Text>
                            <Text
                                style={[styles.floorName, floor.isRestricted && styles.restricted]}
                                numberOfLines={1}
                            >
                                {floor.name}
                            </Text>
                        </View>
                        {elevators.map((elevator) => (
                            <MapCell key={elevator.id} elevator={elevator} floor={floor.id} />
                        ))}
                    </View>
                ))}
            </View>

            <Text style={styles.heading}>Ανελκυστήρες</Text>
            {elevators.map((elevator) => (
                <View key={elevator.id} style={styles.card}>
                    <Text style={styles.cardTitle}>Ανελκυστήρας {elevator.id}</Text>
                    <Text style={styles.text}>Τρέχων Όροφος: {elevator.currentFloor}</Text>
                    <Text style={styles.text}>
                        Κατάσταση: <Text style={styles.statusText}>{statusLabel(elevator.status)}</Text>
                    </Text>
                </View>
            ))}
        </ScrollView>
    );
}

function MapCell({ elevator, floor }: { elevator: Elevator; floor: number }) {
    if (elevator.currentFloor === floor) {
        return (
            <View style={styles.cell}>
                <View style={styles.car}>
                    <Text style={styles.carText}>
                        {elevator.id}
                        {arrow(elevator.status)}
                    </Text>
                </View>
            </View>
        );
    }

    return (
        <View style={styles.cell}>
            {elevator.nextStops.includes(floor) && <View style={styles.stopDot} />}
        </View>
    );
}

function arrow(status: ElevatorStatus): string {
    if (status === "UP") {
        return "▲";
    }
    if (status === "DOWN") {
        return "▼";
    }
    return "";
}

function statusLabel(status: ElevatorStatus): string {
    if (status === "UP") {
        return "Ανεβαίνει";
    }
    if (status === "DOWN") {
        return "Κατεβαίνει";
    }
    return "Σε αναμονή";
}

const styles = StyleSheet.create({
    container: {
        padding: 20,
        paddingTop: 50,
        backgroundColor: "#121212",
        flexGrow: 1,
    },
    title: {
        fontSize: 32,
        fontWeight: "bold",
        textAlign: "center",
        color: "#2387b9",
        letterSpacing: 1,
    },
    subtitle: {
        fontSize: 14,
        textAlign: "center",
        marginBottom: 25,
        color: "#94a3b8",
    },
    heading: {
        fontSize: 20,
        fontWeight: "bold",
        marginTop: 25,
        marginBottom: 12,
        color: "#2996c4",
    },
    card: {
        backgroundColor: "#1e1e1e",
        padding: 16,
        borderRadius: 10,
        marginBottom: 12,
        borderWidth: 1,
        borderColor: "#2c2c2c",
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
        lineHeight: 24,
    },
    statusText: {
        color: "#38bdf8",
        fontStyle: "italic",
    },
    row: {
        flexDirection: "row",
        alignItems: "center",
        height: rowHeight,
    },
    evenRow: {
        backgroundColor: "#2c2c2c",
    },
    headerText: {
        fontSize: 12,
        fontWeight: "bold",
        color: "#94a3b8",
    },
    floorColumn: {
        flex: 1,
        flexDirection: "row",
        alignItems: "center",
    },
    floorNumber: {
        width: 24,
        textAlign: "right",
        marginRight: 6,
        fontWeight: "bold",
        color: "#ffffff",
    },
    floorName: {
        flex: 1,
        fontSize: 12,
        color: "#cbd5e1",
    },
    restricted: {
        color: "rgb(225, 207, 204)",
        fontWeight: "bold",
    },
    cell: {
        width: 38,
        height: rowHeight,
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        borderLeftWidth: 1,
        borderLeftColor: "#2c2c2c",
    },
    car: {
        width: 32,
        height: rowHeight - 4,
        borderRadius: 4,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#2563eb",
    },
    carText: {
        color: "#ffffff",
        fontSize: 11,
        fontWeight: "bold",
    },
    stopDot: {
        width: 6,
        height: 6,
        borderRadius: 3,
        backgroundColor: "#38bdf8",
    },
});
