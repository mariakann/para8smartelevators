import React, { useState } from "react";
import { Modal, Pressable, StyleSheet, Text, View } from "react-native";

type Cabin = {
  id: string;
  floor: number;
  available: boolean;
};

type EmergencyModalProps = {
  visible: boolean;
  onClose: () => void;
  onTriggerEmergency?: (eventType: string) => void;
};

export function EmergencyModal({
  visible,
  onClose,
  onTriggerEmergency,
}: EmergencyModalProps) {
  const [emergencyActive, setEmergencyActive] = useState(false);
  const [cabins, setCabins] = useState<Cabin[]>([
    { id: "A", floor: 0, available: true },
    { id: "B", floor: 4, available: true },
    { id: "C", floor: 9, available: true },
  ]);

  function handleSoundAlarm() {
    setEmergencyActive(true);
    setCabins((current) =>
      current.map((cabin) => ({
        ...cabin,
        floor: 0,
        available: false,
      }))
    );

    if (onTriggerEmergency) {
      onTriggerEmergency("GENERAL_EMERGENCY");
    }
  }

  function handleClose() {
    onClose();
  }

  return (
    <Modal
      visible={visible}
      transparent={true}
      animationType="fade"
      onRequestClose={handleClose}
    >
      <View style={styles.backdrop}>
        <View style={styles.modalCard}>
          <Text style={styles.title}>Έκτακτη ανάγκη</Text>
          <Text style={styles.subtitle}>
            {emergencyActive
              ? "Ο συναγερμός είναι ενεργός — κανένας ανελκυστήρας δεν είναι διαθέσιμος."
              : "Έλεγχος καταστάσης θαλάμων πριν τον συναγερμό."}
          </Text>

          <View style={styles.list}>
            {cabins.map((cabin) => (
              <View key={cabin.id} style={styles.cabinRow}>
                <Text
                  style={[
                    styles.cabinText,
                    { color: cabin.available ? "#4ade80" : "#f87171" },
                  ]}
                >
                  {cabin.id} · όροφος {cabin.floor} ·{" "}
                  {cabin.available ? "ελεύθερος" : "εκτός"}
                </Text>
              </View>
            ))}
          </View>

          <Pressable style={styles.alarmButton} onPress={handleSoundAlarm}>
            <Text style={styles.alarmButtonText}>Συναγερμός</Text>
          </Pressable>

          <Pressable style={styles.closeButton} onPress={handleClose}>
            <Text style={styles.closeButtonText}>Κλείσιμο</Text>
          </Pressable>
        </View>
      </View>
    </Modal>
  );
}

const styles = StyleSheet.create({
  backdrop: {
    flex: 1,
    backgroundColor: "rgba(0, 0, 0, 0.75)",
    justifyContent: "center",
    padding: 20,
  },
  modalCard: {
    backgroundColor: "#1e1e1e",
    borderRadius: 12,
    padding: 20,
    borderWidth: 1,
    borderColor: "#334155",
    gap: 12,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#ffffff",
  },
  subtitle: {
    fontSize: 14,
    color: "#cbd5e1",
  },
  list: {
    gap: 8,
    marginVertical: 4,
  },
  cabinRow: {
    backgroundColor: "#121212",
    padding: 10,
    borderRadius: 8,
  },
  cabinText: {
    fontSize: 16,
    fontWeight: "600",
  },
  alarmButton: {
    backgroundColor: "#991b1b",
    padding: 14,
    borderRadius: 8,
    alignItems: "center",
  },
  alarmButtonText: {
    color: "#ffffff",
    fontSize: 16,
    fontWeight: "bold",
  },
  closeButton: {
    backgroundColor: "#2c2c2c",
    padding: 12,
    borderRadius: 8,
    alignItems: "center",
  },
  closeButtonText: {
    color: "#e2e8f0",
    fontSize: 15,
  },
});