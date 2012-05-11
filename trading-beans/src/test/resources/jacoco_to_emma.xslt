<?xml version="1.0"?>

<!-- Converts Jacoco XML to old Emma XML as good as it is possible 
   - Author: Markus Schlegel, pulinco engineering switzerland
   - Version 0.1, 23.Jan.2012
  -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/report">
		<report>
			<stats>
				<packages>
					<xsl:attribute name="value">
						<xsl:value-of select="count(//package)"/>
					</xsl:attribute>
				</packages>
				<classes>
					<xsl:attribute name="value">
						<xsl:value-of select="counter[@type='CLASS']/@covered + counter[@type='CLASS']/@missed"/>
					</xsl:attribute>
				</classes>
				<methods>
					<xsl:attribute name="value">
						<xsl:value-of select="counter[@type='METHOD']/@covered + counter[@type='METHOD']/@missed"/>
					</xsl:attribute>
				</methods>
				<srcfiles>
					<xsl:attribute name="value">
						<xsl:value-of select="count(//sourcefile)"/>
					</xsl:attribute>
				</srcfiles>
				<srclines>
					<xsl:attribute name="value">
						<xsl:value-of select="counter[@type='LINE']/@covered + counter[@type='LINE']/@missed"/>
					</xsl:attribute>
				</srclines>
			</stats>
			<data>
				<all name="all classes">
					<xsl:for-each select="counter">
						<xsl:call-template name="counter"/>
					</xsl:for-each>
					<xsl:for-each select="group/package">
						<package>
							<xsl:attribute name="name">
								<xsl:value-of select="replace(@name, '/', '.')"/>
							</xsl:attribute>
							<xsl:for-each select="counter">
								<xsl:call-template name="counter"/>
							</xsl:for-each>
							<xsl:for-each select="sourcefile">
								<srcfile>
									<xsl:attribute name="name">
										<xsl:value-of select="replace(@name, '/', '.')"/>
									</xsl:attribute>
									<xsl:for-each select="counter">
										<xsl:call-template name="counter"/>
									</xsl:for-each>
									<xsl:variable name="clsName" select="concat(../@name, '/', substring-before(@name, '.java') ) "/>
									<!-- xsl:value-of select="$clsName"/ -->
									<xsl:for-each select="../class[starts-with(@name,$clsName)]">
										<class>
											<xsl:attribute name="name">
												<xsl:value-of select="substring(substring-after(@name, ../@name), 2)"/>
											</xsl:attribute>
											<xsl:for-each select="counter">
												<xsl:call-template name="counter"/>
											</xsl:for-each>
											<xsl:for-each select="method">
												<xsl:variable name="arguments">
													<xsl:value-of select="replace(substring-before(@desc, ')'), ';', ', ')"/>)</xsl:variable>
												<xsl:variable name="mReturn">
													<xsl:choose>
														<xsl:when test="ends-with(@desc, ')V')">: void</xsl:when>
														<xsl:otherwise>: <xsl:value-of select="substring-after(@desc, ')')"/></xsl:otherwise>
													</xsl:choose>
												</xsl:variable>
												<method>
													<xsl:attribute name="name">
														<xsl:value-of select="concat(@name, ' ', $arguments, replace($mReturn, ';', ''))"/>
													</xsl:attribute>
													<xsl:for-each select="counter">
														<xsl:call-template name="counter"/>
													</xsl:for-each>
												</method>
											</xsl:for-each>
										</class>
									</xsl:for-each>
								</srcfile>
							</xsl:for-each>
						</package>
					</xsl:for-each>
				</all>
			</data>
		</report>
	</xsl:template>

	<xsl:template name="counter">
		<xsl:variable name="emmaType">
			<xsl:choose>
				<xsl:when test="@type='CLASS'">class, %</xsl:when>
				<xsl:when test="@type='METHOD'">method, %</xsl:when>
				<xsl:when test="@type='COMPLEXITY'">block, %</xsl:when>
				<xsl:when test="@type='LINE'">line, %</xsl:when>
				<xsl:otherwise>unknown</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="$emmaType != 'unknown'">
			<coverage>
				<xsl:attribute name="type">
					<xsl:value-of select="$emmaType"/>
				</xsl:attribute>
				<xsl:variable name="total" select="@missed + @covered"/>
				<xsl:attribute name="value">
					<xsl:value-of select="ceiling((1 div $total) * @covered * 100)"/>%  (<xsl:value-of select="@covered"/>/<xsl:value-of select="$total"/>)</xsl:attribute>
			</coverage>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2008. Progress Software Corporation. All rights reserved.

<metaInformation>
	<scenarios>
		<scenario default="no" name="Scenario1" userelativepaths="yes" externalpreview="no" url="coverage.xml" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="no" profilemode="0" profiledepth="" profilelength="" urlprofilexml=""
		          commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="">
			<advancedProp name="sInitialMode" value=""/>
			<advancedProp name="bXsltOneIsOkay" value="true"/>
			<advancedProp name="bSchemaAware" value="false"/>
			<advancedProp name="bXml11" value="false"/>
			<advancedProp name="iValidation" value="0"/>
			<advancedProp name="bExtensions" value="true"/>
			<advancedProp name="iWhitespace" value="0"/>
			<advancedProp name="sInitialTemplate" value=""/>
			<advancedProp name="bTinyTree" value="true"/>
			<advancedProp name="bWarnings" value="true"/>
			<advancedProp name="bUseDTD" value="false"/>
			<advancedProp name="iErrorHandling" value="fatal"/>
		</scenario>
		<scenario default="yes" name="Scenario2" userelativepaths="yes" externalpreview="no" url="coverageOrig.xml" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="no" profilemode="0" profiledepth="" profilelength="" urlprofilexml=""
		          commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="">
			<advancedProp name="sInitialMode" value=""/>
			<advancedProp name="bXsltOneIsOkay" value="true"/>
			<advancedProp name="bSchemaAware" value="false"/>
			<advancedProp name="bXml11" value="false"/>
			<advancedProp name="iValidation" value="0"/>
			<advancedProp name="bExtensions" value="true"/>
			<advancedProp name="iWhitespace" value="0"/>
			<advancedProp name="sInitialTemplate" value=""/>
			<advancedProp name="bTinyTree" value="true"/>
			<advancedProp name="bWarnings" value="true"/>
			<advancedProp name="bUseDTD" value="false"/>
			<advancedProp name="iErrorHandling" value="fatal"/>
		</scenario>
	</scenarios>
	<MapperMetaTag>
		<MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="emma.xsd" destSchemaRoot="report" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no">
			<SourceSchema srcSchemaPath="coverage.xml" srcSchemaRoot="report" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/>
		</MapperInfo>
		<MapperBlockPosition>
			<template match="/">
				<block path="report/data/all/xsl:for-each" x="317" y="224"/>
				<block path="report/data/all/xsl:for-each/package/xsl:for-each" x="317" y="278"/>
				<block path="report/data/all/xsl:for-each/package/xsl:for-each/srcfile/xsl:for-each" x="317" y="332"/>
				<block path="report/data/all/xsl:for-each/package/xsl:for-each/srcfile/xsl:for-each/class/xsl:for-each" x="317" y="386"/>
			</template>
		</MapperBlockPosition>
		<TemplateContext></TemplateContext>
		<MapperFilter side="source"></MapperFilter>
	</MapperMetaTag>
</metaInformation>
-->