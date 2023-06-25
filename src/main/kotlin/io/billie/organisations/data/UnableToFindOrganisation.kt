package io.billie.organisations.data

class UnableToFindOrganisation(val organisationId: String) : RuntimeException("Unable to find organisation by $organisationId")