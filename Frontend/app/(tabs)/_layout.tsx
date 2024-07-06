import React from 'react';
import FontAwesome from '@expo/vector-icons/FontAwesome';
import { FontAwesome5, MaterialIcons, Octicons } from "@expo/vector-icons";
import { Ionicons, AntDesign } from "@expo/vector-icons";

import { Link, Tabs } from 'expo-router';
import { Pressable } from 'react-native';

import Colors from '@/constants/Colors';
import { useColorScheme } from '@/components/useColorScheme';
import { useClientOnlyValue } from '@/components/useClientOnlyValue';

// You can explore the built-in icon families and icons on the web at https://icons.expo.fyi/
function TabBarIcon(props: {
  name: React.ComponentProps<typeof FontAwesome>['name'];
  color: string;
}) {
  return <FontAwesome size={28} style={{ marginBottom: -3 }} {...props} />;
}


export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? "light"].tint,
        // Disable the static render of the header on web
        // to prevent a hydration error in React Navigation v6.
        headerShown: useClientOnlyValue(false, true),
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: "Tab One",
          tabBarIcon: ({ color }) => <TabBarIcon name="code" color={color} />,
          headerRight: () => (
            <Link href="/modal" asChild>
              <Pressable>
                {({ pressed }) => (
                  <FontAwesome
                    name="info-circle"
                    size={25}
                    color={Colors[colorScheme ?? "light"].text}
                    style={{ marginRight: 15, opacity: pressed ? 0.5 : 1 }}
                  />
                )}
              </Pressable>
            </Link>
          ),
        }}
      />
      <Tabs.Screen
        name="EmergencyReport"
        options={{
          title: "신고이력",
          tabBarIcon: ({ color }) => (
            <AntDesign name="notification" size={24} color="black" />
          ),
        }}
      />

      <Tabs.Screen
        name="HeartRateMonitoring"
        options={{
          title: "심박수",
          tabBarIcon: ({ color }) => (
            <FontAwesome5 name="heartbeat" size={24} color="black" />
          ),
        }}
      />

      <Tabs.Screen
        name="SOS"
        options={{
          title: "SOS",
          tabBarIcon: ({ color }) => (
            <MaterialIcons name="sos" size={24} color="black" />
          ),
        }}
      />

      <Tabs.Screen
        name="Notifications"
        options={{
          title: "알림",
          tabBarIcon: ({ color }) => (
            <Octicons name="bell-fill" size={24} color="black" />
          ),
        }}
      />

      <Tabs.Screen
        name="Setting"
        options={{
          title: "설정",
          tabBarIcon: ({ color }) => (
            <Ionicons name="settings" size={24} color="black" />
          ),
        }}
      />
    </Tabs>
  );
}
