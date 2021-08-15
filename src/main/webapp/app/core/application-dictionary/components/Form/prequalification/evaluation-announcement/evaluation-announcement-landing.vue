<template>
    <div class="app-container card-view bidding-process">
        <EmailGrid :pickRow="pickRow"></EmailGrid>
    </div>
</template>

<script lang="ts" src="./evaluation-announcement-landing.component.ts">
</script>

<style lang="scss">
.compact .bidding-process {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    .joined-vendor-dialog .el-table.vendor-list {
        td {
            height: 35px;
        }
    }
}

.el-table__fixed, .el-table__fixed-right{
    box-shadow: none;
}

.main {
    padding: 0px;

    .button {
        width: 100%;
    }
}

.toolbar {
    padding: 4px 16px;
}

.form-input {
    width: 100%;
}

</style>
