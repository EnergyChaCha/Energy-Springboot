import React from "react";
import { Text, View } from "./../Themed";
import { StyleSheet, TextInput } from "react-native";

function SignUpForm() {
  return (
    <View>
      <Text style={styles.header}>회원가입</Text>

      <Text style={styles.header}>이름</Text>
      <TextInput style={styles.input}></TextInput>
    </View>
  );
}

const styles = StyleSheet.create({
  header: {
    fontSize: 20,
  },
  input: {
    backgroundColor: "#eeeeee"
  },
});


export default SignUpForm;