<template>
  <div>
    <b-dropdown-item to="/region">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.region')">Region</span>
    </b-dropdown-item>
    <b-dropdown-item to="/district">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.district')">District</span>
    </b-dropdown-item>
    <b-dropdown-item to="/announcement">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.announcement')">Announcement</span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.category')">Category</span>
    </b-dropdown-item>
    <b-dropdown-item to="/sub-category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.subCategory')">Sub Category</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
