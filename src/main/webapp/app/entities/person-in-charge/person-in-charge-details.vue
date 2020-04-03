<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="personInCharge">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.personInCharge.detail.title')">PersonInCharge</span> {{personInCharge.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.personInCharge.position')">Position</span>
                    </dt>
                    <dd>
                        <span>{{personInCharge.position}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.personInCharge.phone')">Phone</span>
                    </dt>
                    <dd>
                        <span>{{personInCharge.phone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.personInCharge.user')">User</span>
                    </dt>
                    <dd>
                        {{personInCharge.userLogin}}
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.personInCharge.vendor')">Vendor</span>
                    </dt>
                    <dd>
                        <div v-if="personInCharge.vendorId">
                            <router-link :to="{name: 'VendorView', params: {vendorId: personInCharge.vendorId}}">{{personInCharge.vendorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="personInCharge.id" :to="{name: 'PersonInChargeEdit', params: {personInChargeId: personInCharge.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./person-in-charge-details.component.ts">
</script>
