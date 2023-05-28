<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

<table cellpadding="2" cellspacing="2" border="1">

<tr>

<th>ID</th>

<th>Name</th>

<th>Address</th>

</tr>

<xsl:apply-templates select="EmpDetails" />

</table>

</xsl:template>

<xsl:template match="EmpDetails">

<xsl:apply-templates select="Employee" />

</xsl:template>

<xsl:template match="Employee">

<tr>

<td>

<xsl:value-of select="id"></xsl:value-of>

</td>

<td>

<xsl:value-of select="name"></xsl:value-of>

</td>

<td>

<xsl:value-of select="address"></xsl:value-of>

</td>

</tr>

</xsl:template>

</xsl:stylesheet>