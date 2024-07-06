import { StyleSheet, Button } from "react-native";
import { Link } from "expo-router";
import { Text, View } from "@/components/Themed";
import SignUpForm from "@/components/signup/SignUpForm";

interface SignUpScreenProps {
  navigation: {
    replace: (routeName: string) => void;
  };
}

export default function ModalScreen({
  navigation,
}: {
  navigation: {
    replace: (routeName: string) => void;
  };
}) {
  return (
    <View style={styles.container}>
      <SignUpForm />
      <Text>회원가입 페이지</Text>
      <Link href="/(tabs)">View details</Link>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: "80%",
  },
});
