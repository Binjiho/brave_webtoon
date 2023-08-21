<script setup>
import { defineAsyncComponent } from "vue";
const props = defineProps({
    name: {
        type: String,
        required: true,
    },
});

const name = props.name;
const root = name.match(/[a-z]+(?=\/)/g);
const fileName = name.match(/(?<=\/)[a-z-_]+/g);

const icon = defineAsyncComponent(() =>
    import(`../../../assets/iconsets/svg/${root}/${fileName}.svg?component`)
);
</script>

<template>
    <component :is="icon" />
</template>
