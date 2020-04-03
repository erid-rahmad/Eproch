<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.referenceList.home.createOrEditLabel" v-text="$t('opusWebApp.referenceList.home.createOrEditLabel')">Create or edit a ReferenceList</h2>
                <div>
                    <div class="form-group" v-if="referenceList.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="referenceList.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.referenceList.name')" for="reference-list-name">Name</label>
                        <input type="text" class="form-control" name="name" id="reference-list-name"
                            :class="{'valid': !$v.referenceList.name.$invalid, 'invalid': $v.referenceList.name.$invalid }" v-model="$v.referenceList.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.referenceList.description')" for="reference-list-description">Description</label>
                        <input type="text" class="form-control" name="description" id="reference-list-description"
                            :class="{'valid': !$v.referenceList.description.$invalid, 'invalid': $v.referenceList.description.$invalid }" v-model="$v.referenceList.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.referenceList.value')" for="reference-list-value">Value</label>
                        <input type="text" class="form-control" name="value" id="reference-list-value"
                            :class="{'valid': !$v.referenceList.value.$invalid, 'invalid': $v.referenceList.value.$invalid }" v-model="$v.referenceList.value.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.referenceList.reference')" for="reference-list-reference">Reference</label>
                        <select class="form-control" id="reference-list-reference" name="reference" v-model="referenceList.referenceId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="referenceOption.id" v-for="referenceOption in references" :key="referenceOption.id">{{referenceOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.referenceList.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./reference-list-update.component.ts">
</script>
